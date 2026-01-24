import { Injectable, Inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

import { ApiFamily } from '../models';

import { ApiService } from './api-service';
import { ServiceBase } from './service-base';

/**
 * Service for obtaining family objects.
 */
@Injectable()
export class FamilyService extends ServiceBase<ApiFamily> {
  constructor(@Inject(HttpClient) http: HttpClient) {
    super(http);
  }

  url(db): string {
    return this.baseUrl(db) + '/families';
  }
}
