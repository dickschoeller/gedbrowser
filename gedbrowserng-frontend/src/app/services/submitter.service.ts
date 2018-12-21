import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

import { ApiSubmitter } from '../models/api-submitter.model';
import { ApiService } from './api-service';
import { ServiceBase } from './service-base';

/**
 * Service for obtaining submitter objects.
 */
@Injectable()
export class SubmitterService extends ServiceBase<ApiSubmitter> {
  url(db): string {
    return this.baseUrl(db) + '/submitters';
  }
}
