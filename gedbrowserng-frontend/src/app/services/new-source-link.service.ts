import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { ApiSource } from '../models';
import { UrlBuilder } from '../utils';

import { CrudRelated } from './crud-related';

@Injectable()
export class NewSourceLinkService implements CrudRelated<ApiSource> {
  constructor(private http: HttpClient) {}

  post(ub: UrlBuilder, id: string, source: ApiSource): Observable<ApiSource> {
    return this.http.post<ApiSource>(ub.url(id), source);
  }

  put(ub: UrlBuilder, id: string, source: ApiSource): Observable<ApiSource> {
    return this.http.put<ApiSource>(ub.url(id), source);
  }

  delete(ub: UrlBuilder, id: string, source: ApiSource): Observable<ApiSource> {
    return this.http.delete<ApiSource>(ub.url(id, source.string));
  }
}
