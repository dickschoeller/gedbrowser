import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs/Observable';

import { ApiHead } from '../models';

import { UrlBuilder } from '../utils/urlbuilder';

@Injectable()
export class HeadService {

  constructor(private http: HttpClient) {}

  getOne(db: string): Observable<ApiHead> {
    return this.http.get<ApiHead>(this.url(db));
  }

  put(db: string, head: ApiHead): Observable<ApiHead> {
    return this.http.put<ApiHead>(this.url(db), head);
  }

  url(db) {
    const ub = new UrlBuilder(db, '', '');
    return ub.baseUrl();
  }
}
