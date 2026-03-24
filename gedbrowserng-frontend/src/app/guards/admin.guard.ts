import { Injectable } from '@angular/core';
import { Router, CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot } from '@angular/router';
import { UserService } from '../services';

@Injectable()
export class AdminGuard implements CanActivate {
    constructor(private readonly router: Router, private readonly userService: UserService) { }

    canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): boolean {
        if (!this.userService.currentUser) {
            console.log('NOT AN ADMIN ROLE');
            this.router.navigate(['/login'], { queryParams: { returnUrl: state.url } });
            return false;
        }

        const isAdmin = JSON.stringify(this.userService.currentUser.roles).includes('ROLE_ADMIN');
        if (isAdmin) {
            return true;
        } else {
            this.router.navigate(['/403']);
            return false;
        }
    }
}
