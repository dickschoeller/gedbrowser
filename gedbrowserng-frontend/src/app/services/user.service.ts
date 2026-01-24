import { Inject, Injectable } from '@angular/core';
import { map } from 'rxjs/operators';

import { AuthApiService } from './auth-api.service';
import { ConfigService } from './config.service';

@Injectable()
export class UserService {

  currentUser: User;

  constructor(
    @Inject(AuthApiService) private apiService: AuthApiService,
    @Inject(ConfigService) private config: ConfigService
  ) { }

  initUser(): Promise<any> {
      // Race the refresh request against a short timeout so the APP_INITIALIZER
      // does not prevent the app from bootstrapping if the backend is slow or
      // unreachable. If the refresh completes later it will still update currentUser.
      const refreshPromise = this.apiService.get(this.config.refresh_token_url).toPromise()
          .then(res => {
              if (res && res.accessToken !== undefined && res.accessToken !== null) {
                  return this.getMyInfo().toPromise().then((user: User) => this.currentUser = user );
              }
              return null;
          })
          .catch(() => null);

      const timeout = new Promise(resolve => setTimeout(() => resolve(null), 2000));

      return Promise.race([refreshPromise, timeout]);
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
