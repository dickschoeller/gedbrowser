import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs/Observable';

import {ApiSource} from '../models/api-source.model';
import {ApiService} from './api-service';
import { ServiceBase } from './service-base';

/**
 * Service for obtaining source objects.
 */
@Injectable()
export class SourceService extends ServiceBase<ApiSource> {
  url(db): string {
    return this.baseUrl(db) + '/sources';
  }
}
