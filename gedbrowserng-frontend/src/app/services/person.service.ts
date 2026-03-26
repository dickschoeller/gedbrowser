import { Injectable, Inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';

import { ApiPerson } from '../models';
import { ServiceBase } from './service-base';

/**
 * Service for obtaining person objects.
 */
@Injectable()
export class PersonService extends ServiceBase<ApiPerson> {
  constructor(@Inject(HttpClient) http: HttpClient) {
    super(http);
  }

  url(db): string {
    return this.baseUrl(db) + '/persons';
  }
}
