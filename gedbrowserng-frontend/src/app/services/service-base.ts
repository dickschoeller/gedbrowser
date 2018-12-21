import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

import { ApiService } from './api-service';
import { ApiObject } from '../models';
import { UrlBuilder } from '../utils/urlbuilder';

@Injectable()
export abstract class ServiceBase<T extends ApiObject> implements ApiService<T> {
  constructor(private http: HttpClient) {}

  abstract url(db: string);

  post(db: string, data: T): Observable<T> {
    return this.http.post<T>(this.url(db), data);
  }

  getAll(db: string): Observable<Array<T>> {
    return this.http.get<Array<T>>(this.url(db));
  }

  getOne(db: string, id): Observable<T> {
    return this.http.get<T>(this.url(db) + '/' + id);
  }

  put(db: string, data: T): Observable<T> {
    return this.http.put<T>(this.url(db) + '/' + data.string, data);
  }

  delete(db: string, data: T): Observable<T> {
    return this.http.delete<T>(this.url(db) + '/' + data.string);
  }

  postLink(ub: UrlBuilder, id: string, data: T): Observable<T> {
    return this.http.post<T>(ub.url(id), data);
  }

  putLink(ub: UrlBuilder, id: string, data: T): Observable<T> {
    return this.http.put<T>(ub.url(id), data);
  }

  deleteLink(ub: UrlBuilder, id: string, data: T): Observable<T> {
    return this.http.delete<T>(ub.url(id, data.string));
  }

  baseUrl(db: string) {
    const ub = new UrlBuilder(db, '', '');
    return ub.baseUrl();
  }
}
