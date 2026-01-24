import { Inject, Injectable } from '@angular/core';
import { AuthApiService } from './auth-api.service';
import { ConfigService } from './config.service';

@Injectable()
export class FooService {

  constructor(
    @Inject(AuthApiService) private apiService: AuthApiService,
    @Inject(ConfigService) private config: ConfigService
  ) { }

  getFoo() {
    return this.apiService.get(this.config.foo_url);
  }

}
