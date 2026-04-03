import { Injectable, Inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';

import { ApiSource } from '../models';
import { ServiceBase } from './service-base';

/**
 * Service for obtaining source objects.
 */
@Injectable()
export class SourceService extends ServiceBase<ApiSource> {
  constructor(@Inject(HttpClient) http: HttpClient) {
    super(http);
  }

  url(db): string {
    return this.baseUrl(db) + '/sources';
  }
}
