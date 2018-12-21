import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

import { ApiPerson } from '../models';
import { ServiceBase } from './service-base';

/**
 * Service for obtaining person objects.
 */
@Injectable()
export class PersonService extends ServiceBase<ApiPerson> {
  url(db): string {
    return this.baseUrl(db) + '/persons';
  }
}
