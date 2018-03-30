import {HttpClient} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {Observable} from 'rxjs/Observable';

import {ApiObject} from '../models';
import {ApiService} from './api-service';
import {UrlBuilder} from './urlbuilder';

@Injectable()
export abstract class ServiceBase<T extends ApiObject> implements ApiService<T> {
  constructor(private http: HttpClient) {}

  abstract url(db);

  post(db, source: T): Observable<T> {
    return this.http.post<T>(this.url(db), source);
  }

  getAll(db): Observable<Array<T>> {
    return this.http.get<Array<T>>(this.url(db));
  }

  getOne(db, id): Observable<T> {
    return this.http.get<T>(this.url(db) + '/' + id);
  }

  put(db, source: T): Observable<T> {
    return this.http.put<T>(this.url(db) + '/' + source.string, source);
  }

  delete(db, source: T): Observable<T> {
    return this.http.delete<T>(this.url(db) + '/' + source.string);
  }

  baseUrl(db) {
    const ub = new UrlBuilder(db);
    return ub.baseUrl();
  }
}
