import { Injectable } from '@angular/core';
import { HttpClient, HttpEvent } from '@angular/common/http';
import { Observable } from 'rxjs';

import { UrlBuilder } from '../utils';
// Angular 21 prefers plain params objects for HttpClient options — avoid using
// `HttpParams` here so the service is compatible with the newer approach.

/**
 * Service for obtaining saving a db.
 */
@Injectable({ providedIn: 'root' })
export class UploadService {
  constructor(private http: HttpClient) {}

  uploadGedFile(file: File): Observable<HttpEvent<Object>> {
    const ub = new UrlBuilder(undefined, '', '');
    const formdata: FormData = new FormData();
    formdata.append('file', file);
    // Use HttpClient.post with `observe: 'events'` and `reportProgress: true`
    // to receive upload progress events in Angular 21.
    return this.http.post<Object>(ub.baseUrl() + '/upload', formdata, {
      reportProgress: true,
      observe: 'events' as const,
      responseType: 'json' as const
    });
  }
}
