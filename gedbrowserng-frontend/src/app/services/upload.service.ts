import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { UrlBuilder } from '../utils';
import { HttpParams } from '@angular/common/http';
import { HttpEvent } from '@angular/common/http';
import { HttpRequest } from '@angular/common/http';

/**
 * Service for obtaining saving a db.
 */
@Injectable()
export class UploadService {
  constructor(private http: HttpClient) {}

  uploadGedFile(file: File): Observable<Object> {
    const headers = new HttpHeaders();
    headers.set('Content-Type', null);
    headers.set('Accept', 'multipart/form-data');
    const params = new HttpParams();
    const ub = new UrlBuilder(undefined, '', '');
    const formdata: FormData = new FormData;
    formdata.append('file', file);
    return this.http.post(ub.baseUrl() + '/upload', formdata, { params, headers });
  }
}
