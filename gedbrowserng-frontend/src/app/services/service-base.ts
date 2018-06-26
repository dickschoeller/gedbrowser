import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs/Observable';

import { ApiObject } from '../models';

import { ApiService } from './api-service';
import { UrlBuilder } from '../utils/urlbuilder';

@Injectable()
export abstract class ServiceBase<T extends ApiObject> implements ApiService<T> {
  constructor(private http: HttpClient) {}

  abstract url(db: string);

  post(db: string, source: T): Observable<T> {
    return this.http.post<T>(this.url(db), source);
  }

  getAll(db: string): Observable<Array<T>> {
    return this.http.get<Array<T>>(this.url(db));
  }

  getOne(db: string, id): Observable<T> {
    return this.http.get<T>(this.url(db) + '/' + id);
  }

  put(db: string, source: T): Observable<T> {
    return this.http.put<T>(this.url(db) + '/' + source.string, source);
  }

  delete(db: string, source: T): Observable<T> {
    return this.http.delete<T>(this.url(db) + '/' + source.string);
  }

  baseUrl(db: string) {
    const ub = new UrlBuilder(db, '', '');
    return ub.baseUrl();
  }
}
