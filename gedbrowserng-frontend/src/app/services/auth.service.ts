import { Injectable } from '@angular/core';
import { HttpHeaders } from '@angular/common/http';
import { AuthApiService } from './auth-api.service';
import { UserService } from './user.service';
import { ConfigService } from './config.service';
import { map } from 'rxjs/operators';

@Injectable()
export class AuthService {

    constructor(
        private apiService: AuthApiService,
        private userService: UserService,
        private config: ConfigService,
    ) { }

    login(user) {
        const loginHeaders = new HttpHeaders({
            'Accept': 'application/json',
            'Content-Type': 'application/x-www-form-urlencoded'
        });
        const body = `username=${user.username}&password=${user.password}`;
        return this.apiService.post(this.config.login_url, body, loginHeaders).pipe(map(() => {
            this.userService.getMyInfo().subscribe();
        }));
    }

    signup(user) {
        const signupHeaders = new HttpHeaders({
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        });
        return this.apiService.post(this.config.signup_url, JSON.stringify(user), signupHeaders).pipe(map(() => {
            console.log('Sign up success');
        }));
    }

    logout() {
        return this.apiService.post(this.config.logout_url, {})
            .pipe(map(() => {
                this.userService.currentUser = null;
            }));
    }

    changePassowrd(passwordChanger) {
        return this.apiService.post(this.config.change_password_url, passwordChanger);
    }

}
