import { Component, OnInit } from '@angular/core';
import { AuthService, UserService, User } from '../../services';
import { Router } from '@angular/router';

@Component({
    selector: 'app-account-menu',
    templateUrl: './account-menu.component.html',
    styleUrls: ['./account-menu.component.css']
})
export class AccountMenuComponent implements OnInit {

    user: User;

    constructor(
        private authService: AuthService,
        private userService: UserService,
        private router: Router,
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
