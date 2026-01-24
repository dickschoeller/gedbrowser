import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService, UserService } from '../../services';

@Component({
    standalone: false,
    selector: 'app-user-buttons',
    template: `<!-- div fxFlex="1 1 auto" fxLayout="row" fxLayoutAlign="flex-end center" -->
<div>
  <button *ngIf="!hasSignedIn()" [routerLink]="['/signup', { returnUrl: currentUrl() } ]"
          routerLinkActive="router-link-active" mat-button mat-ripple>
    <span>Sign up</span>
  </button>
  <button *ngIf="!hasSignedIn()" [routerLink]="['/login', { returnUrl: currentUrl() } ]"
          routerLinkActive="router-link-active" mat-button mat-ripple>
    <span>Login</span>
  </button>
  <button class="greeting-button" *ngIf="hasSignedIn()" mat-button mat-ripple [matMenuTriggerFor]="accountMenu">
    <span>{{ userName() }}</span>
  </button>
  <button class="greeting-hamburger" *ngIf="hasSignedIn()" mat-icon-button mat-ripple [matMenuTriggerFor]="accountMenu">
    <mat-icon>menu</mat-icon>
  </button>
  <mat-menu #accountMenu class="app-header-accountMenu" yposition="below" [overlapTrigger]="false">
    <app-account-menu></app-account-menu>
  </mat-menu>
</div>`,
    styles: []
})
export class UserButtonsComponent implements OnInit {

    constructor(
        private authService: AuthService,
        private userService: UserService,
        private router: Router
    ) { }

    ngOnInit() {
    }

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
