import { HttpClient } from '@angular/common/http';
import { Inject, Injectable } from '@angular/core';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { tap } from 'rxjs/operators';

import { ApiHead } from '../models';

import { UrlBuilder } from '../utils/urlbuilder';

@Injectable()
export class HeadService {

  constructor(@Inject(HttpClient) private readonly http: HttpClient) {}

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

  private logAttempt(operation: string, url: string) {
    console.info(`[API ${operation}] attempt`, { url });
  }

  getOne(db: string): Observable<ApiHead> {
    const url = this.url(db);
    this.logAttempt('GET', url);
    return this.http.get<ApiHead>(url, this.requestOptions())
      .pipe(catchError(this.logAndRethrow('GET', url)));
  }

  put(db: string, head: ApiHead): Observable<ApiHead> {
    const url = this.url(db);
    this.logAttempt('PUT', url);
    return this.http.put<ApiHead>(url, head, this.requestOptions())
      .pipe(tap(() => console.info('[API PUT] success', { url })))
      .pipe(catchError(this.logAndRethrow('PUT', url)));
  }

  url(db) {
    const ub = new UrlBuilder(db, '', '');
    return ub.baseUrl();
  }
}
