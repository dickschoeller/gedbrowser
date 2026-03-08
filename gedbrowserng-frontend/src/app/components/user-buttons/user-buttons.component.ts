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
    templateUrl: './user-buttons.component.html',
    styleUrls: ['./user-buttons.component.css'],
    imports: [MatButton, RouterLinkActive, RouterLink, MatMenuTrigger, MatIconButton, MatIcon, MatMenu, AccountMenuComponent]
})
export class UserButtonsComponent {

    constructor(
        @Inject(AuthService) private readonly authService: AuthService,
        @Inject(UserService) private readonly userService: UserService,
        @Inject(Router) private readonly router: Router
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
