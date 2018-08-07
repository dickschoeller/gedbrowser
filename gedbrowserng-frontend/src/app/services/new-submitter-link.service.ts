import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { ApiSubmitter } from '../models';
import { UrlBuilder } from '../utils';

import { CrudRelated } from './crud-related';

@Injectable()
export class NewSubmitterLinkService implements CrudRelated<ApiSubmitter> {
  constructor(private http: HttpClient) {}

  post(ub: UrlBuilder, id: string, submitter: ApiSubmitter): Observable<ApiSubmitter> {
    return this.http.post<ApiSubmitter>(ub.url(id), submitter);
  }

  put(ub: UrlBuilder, id: string, submitter: ApiSubmitter): Observable<ApiSubmitter> {
    return this.http.put<ApiSubmitter>(ub.url(id), submitter);
  }

  delete(ub: UrlBuilder, id: string, submitter: ApiSubmitter): Observable<ApiSubmitter> {
    return this.http.delete<ApiSubmitter>(ub.url(id, submitter.string));
  }
}
