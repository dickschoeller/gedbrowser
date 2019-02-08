import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService, UserService } from '../../services';

@Component({
    selector: 'app-user-buttons',
    templateUrl: './user-buttons.component.html',
    styleUrls: ['./user-buttons.component.css']
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
