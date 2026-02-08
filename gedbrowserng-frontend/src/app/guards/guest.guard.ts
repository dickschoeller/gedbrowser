import { Injectable } from '@angular/core';
import { Router, CanActivate } from '@angular/router';
import { UserService } from '../services';

@Injectable()
export class GuestGuard implements CanActivate {

    constructor(private readonly router: Router, private readonly userService: UserService) { }

    canActivate(): boolean {
        if (this.userService.currentUser) {
            this.router.navigate(['/']);
            return false;
        } else {
            return true;
        }
    }
}
