import { Component, OnInit , Inject } from '@angular/core';
import { AuthService, UserService, User } from '../../services';
import { Router } from '@angular/router';
import { MatMenuItem } from '@angular/material/menu';

@Component({
    selector: 'app-account-menu',
    template: `<!-- button mat-menu-item [routerLink]="['/change-password']">CHANGE PASSWORD</button -->
<button mat-menu-item (click)="logout()">Sign out</button>`,
    styles: [],
    imports: [MatMenuItem]
})
export class AccountMenuComponent implements OnInit {

    user: User;

    constructor(
        @Inject(AuthService) @Inject(AuthService) @Inject(AuthService) @Inject(AuthService) private authService: AuthService,
        @Inject(UserService) @Inject(UserService) @Inject(UserService) private userService: UserService,
        @Inject(Router) @Inject(Router) @Inject(Router) private router: Router,
    ) { }

    ngOnInit() {
        this.user = this.userService.currentUser;
    }

    logout() {
        this.authService.logout().subscribe(() => this.router.navigate([this.currentUrl()]));
    }

    currentUrl() {
        return this.router.url;
    }
}
