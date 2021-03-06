import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

import { ApiFamily } from '../models';

import { ApiService } from './api-service';
import { ServiceBase } from './service-base';

/**
 * Service for obtaining family objects.
 */
@Injectable()
export class FamilyService extends ServiceBase<ApiFamily> {
  url(db): string {
    return this.baseUrl(db) + '/families';
  }
}
