import { Component, Inject } from '@angular/core';
import { Router, RouterLinkActive, RouterLink } from '@angular/router';
import { AuthService, UserService } from '../../services';
import { MatButton, MatIconButton } from '@angular/material/button';
import { MatMenuTrigger, MatMenu } from '@angular/material/menu';
import { MatIcon } from '@angular/material/icon';
import { AccountMenuComponent } from '../account-menu/account-menu.component';

@Component({
    selector: 'app-user-buttons',
    standalone: true,
    template: `<!-- div fxFlex="1 1 auto" fxLayout="row" fxLayoutAlign="flex-end center" -->
<div>
  @if (!hasSignedIn()) {
    <button [routerLink]="['/signup', { returnUrl: currentUrl() } ]"
            routerLinkActive="router-link-active" mat-button mat-ripple>
      <span>Sign up</span>
    </button>
  }
  @if (!hasSignedIn()) {
    <button [routerLink]="['/login', { returnUrl: currentUrl() } ]"
            routerLinkActive="router-link-active" mat-button mat-ripple>
      <span>Login</span>
    </button>
  }
  @if (hasSignedIn()) {
    <button class="greeting-button" mat-button mat-ripple [matMenuTriggerFor]="accountMenu">
      <span>{{ userName() }}</span>
    </button>
  }
  @if (hasSignedIn()) {
    <button class="greeting-hamburger" mat-icon-button mat-ripple [matMenuTriggerFor]="accountMenu">
      <mat-icon>menu</mat-icon>
    </button>
  }
  <mat-menu #accountMenu class="app-header-accountMenu" yposition="below" [overlapTrigger]="false">
    <app-account-menu></app-account-menu>
  </mat-menu>
</div>`,
    styles: [],
    imports: [MatButton, RouterLinkActive, RouterLink, MatMenuTrigger, MatIconButton, MatIcon, MatMenu, AccountMenuComponent]
})
export class UserButtonsComponent {

    constructor(
        @Inject(AuthService) @Inject(AuthService) @Inject(AuthService) @Inject(AuthService) private readonly authService: AuthService,
        @Inject(UserService) @Inject(UserService) @Inject(UserService) private readonly userService: UserService,
        @Inject(Router) @Inject(Router) @Inject(Router) private readonly router: Router
    ) { }

    logout() {
        this.authService.logout().subscribe(() => {
            this.router.navigate(['/login']);
        });
    }

    hasSignedIn() {
        return !!this.userService.currentUser;
    }

    userName() {
        const user = this.userService.currentUser;
        return user.firstname + ' ' + user.lastname;
    }

    currentUrl() {
        return this.router.url;
    }
}
