import { ApplicationRef, Component, Inject, NgZone, OnDestroy, OnInit } from '@angular/core';
import { Router, RouterOutlet } from '@angular/router';
import { Subscription, timer } from 'rxjs';
import { take } from 'rxjs/operators';

import { PersonService, UserService } from './services';

@Component({
    selector: 'app-root',
    standalone: true,
    template: `<div class="body">
  <router-outlet></router-outlet>
</div>`,
    styles: [],
    imports: [RouterOutlet]
})
export class AppComponent implements OnInit, OnDestroy {
  private static readonly SESSION_CHECK_INTERVAL_MS = 60_000;
  private sessionCheckSubscription?: Subscription;

  constructor(
    @Inject(ApplicationRef) private readonly appRef: ApplicationRef,
    @Inject(NgZone) private readonly zone: NgZone,
    @Inject(UserService) private readonly userService: UserService,
    @Inject(PersonService) private readonly personService: PersonService,
    @Inject(Router) private readonly router: Router,
  ) { }

  ngOnInit() {
    this.sessionCheckSubscription = timer(0, AppComponent.SESSION_CHECK_INTERVAL_MS).subscribe(() => {
      this.checkSessionStillValid();
    });
  }

  ngOnDestroy() {
    this.sessionCheckSubscription?.unsubscribe();
  }

  checkSessionStillValid() {
    if (!this.userService.currentUser) {
      return;
    }

    this.userService.getMyInfo().pipe(take(1)).subscribe({
      next: (user) => {
        this.runInAngular(() => {
          this.userService.currentUser = user;
        });
      },
      error: (error) => {
        if (!this.isAuthError(error)) {
          return;
        }

        this.runInAngular(() => {
          this.userService.currentUser = null;
        });
        this.handlePostExpiryNavigation();
      }
    });
  }

  private handlePostExpiryNavigation() {
    const primarySegments = this.router.parseUrl(this.router.url).root.children['primary']?.segments ?? [];
    if (primarySegments.length < 3 || primarySegments[1]?.path !== 'persons') {
      this.refreshCurrentRoute();
      return;
    }

    const dataset = primarySegments[0]?.path;
    const personId = primarySegments[2]?.path;
    if (!dataset || !personId) {
      this.refreshCurrentRoute();
      return;
    }

    this.personService.getOne(dataset, personId).pipe(take(1)).subscribe({
      next: () => {
        // Person is visible to anonymous users; refresh route content for anonymous view.
        this.refreshCurrentRoute();
      },
      error: (error) => {
        if (error?.status === 401 || error?.status === 403 || error?.status === 404) {
          this.runInAngular(() => {
            void this.router.navigateByUrl(`/${dataset}/persons`, { replaceUrl: true });
          });
          return;
        }

        this.refreshCurrentRoute();
      }
    });
  }

  private refreshCurrentRoute(): void {
    const currentUrl = this.router.url;
    this.runInAngular(() => {
      void this.router.navigateByUrl(currentUrl, { replaceUrl: true });
    });
  }

  private runInAngular(action: () => void): void {
    this.zone.run(() => {
      action();
      this.appRef.tick();
    });
  }

  private isAuthError(error: any): boolean {
    return error?.status === 401 || error?.status === 403;
  }
}
