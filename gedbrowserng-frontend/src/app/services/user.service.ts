import { Injectable } from '@angular/core';
import { map } from 'rxjs/operators';

import { AuthApiService } from './auth-api.service';
import { ConfigService } from './config.service';

@Injectable()
export class UserService {

  currentUser: User;

  constructor(
    private apiService: AuthApiService,
    private config: ConfigService
  ) { }

  initUser(): Promise<any> {
      const promise = this.apiService.get(this.config.refresh_token_url).toPromise()
          .then(res => {
              if (res.accessToken !== undefined && res.accessToken !== null) {
                  return this.getMyInfo().toPromise().then((user: User) => this.currentUser = user );
              }
          })
          .catch(() => null);
      return promise;
  }

  resetCredentials() {
    return this.apiService.get(this.config.reset_credentials_url);
  }

  getMyInfo() {
    return this.apiService.get(this.config.whoami_url).pipe(map((user: User) => this.currentUser = user));
  }

  getAll() {
    return this.apiService.get(this.config.users_url);
  }

}

export interface User {
    username: string;
    firstname: string;
    lastname: string;
    email: string;
    password: string;
    roles: string[];
}
