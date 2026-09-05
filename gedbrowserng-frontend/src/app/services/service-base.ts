import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, map, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { tap } from 'rxjs/operators';

import { ApiService } from './api-service';
import { ApiObject } from '../models';
import { UrlBuilder } from '../utils/urlbuilder';

@Injectable()
export abstract class ServiceBase<T extends ApiObject> implements ApiService<T> {
  constructor(private readonly http: HttpClient) {}

  private requestOptions() {
    return { withCredentials: true };
  }

  private logAndRethrow(operation: string, url: string) {
    return (error: any) => {
      const status = error?.status;
      const message = error?.message || error?.statusText || 'unknown error';
      console.error(`[API ${operation}] ${url} failed`, {
        status,
        message,
        error
      });
      return throwError(() => error);
    };
  }

  private logAttempt(operation: string, url: string, data?: T) {
    console.info(`[API ${operation}] attempt`, {
      url,
      objectId: data?.string
    });
  }

  abstract url(db: string);

  post(db: string, data: T): Observable<T> {
    const url = this.url(db);
    this.logAttempt('POST', url, data);
    return this.http.post<T>(url, data, this.requestOptions())
      .pipe(catchError(this.logAndRethrow('POST', url)));
  }

  getAll(db: string): Observable<Array<T>> {
    const url = this.url(db);
    this.logAttempt('GET', url);
    return this.http.get<Array<T>>(url, this.requestOptions()).pipe(
      map((items) => {
        const seen = new Set<string>();
        return items.filter((item) => {
          const key = item?.string;
          if (!key || seen.has(key)) {
            return false;
          }
          seen.add(key);
          return true;
        });
      }),
      catchError(this.logAndRethrow('GET', url))
    );
  }

  getOne(db: string, id): Observable<T> {
    const url = this.url(db) + '/' + id;
    this.logAttempt('GET', url);
    return this.http.get<T>(url, this.requestOptions())
      .pipe(catchError(this.logAndRethrow('GET', url)));
  }

  put(db: string, data: T): Observable<T> {
    const url = this.url(db) + '/' + data.string;
    this.logAttempt('PUT', url, data);
    return this.http.put<T>(url, data, this.requestOptions())
      .pipe(tap(() => console.info(`[API PUT] success`, { url, objectId: data?.string })))
      .pipe(catchError(this.logAndRethrow('PUT', url)));
  }

  delete(db: string, data: T): Observable<T> {
    const url = this.url(db) + '/' + data.string;
    this.logAttempt('DELETE', url, data);
    return this.http.delete<T>(url, this.requestOptions())
      .pipe(catchError(this.logAndRethrow('DELETE', url)));
  }

  postLink(ub: UrlBuilder, id: string, data: T): Observable<T> {
    const url = ub.url(id);
    this.logAttempt('POST', url, data);
    return this.http.post<T>(url, data, this.requestOptions())
      .pipe(catchError(this.logAndRethrow('POST', url)));
  }

  putLink(ub: UrlBuilder, id: string, data: T): Observable<T> {
    const url = ub.url(id);
    this.logAttempt('PUT', url, data);
    return this.http.put<T>(url, data, this.requestOptions())
      .pipe(catchError(this.logAndRethrow('PUT', url)));
  }

  deleteLink(ub: UrlBuilder, id: string, data: T): Observable<T> {
    const url = ub.url(id, data.string);
    this.logAttempt('DELETE', url, data);
    return this.http.delete<T>(url, this.requestOptions())
      .pipe(catchError(this.logAndRethrow('DELETE', url)));
  }

  baseUrl(db: string) {
    const ub = new UrlBuilder(db, '', '');
    return ub.baseUrl();
  }
}
