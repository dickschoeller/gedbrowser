import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { ApiNote } from '../models';
import { UrlBuilder } from '../utils';

import { CrudRelated } from './crud-related';

@Injectable()
export class NewNoteLinkService implements CrudRelated<ApiNote> {
  constructor(private http: HttpClient) {}

  post(ub: UrlBuilder, id: string, note: ApiNote): Observable<ApiNote> {
    return this.http.post<ApiNote>(ub.url(id), note);
  }

  put(ub: UrlBuilder, id: string, note: ApiNote): Observable<ApiNote> {
    return this.http.put<ApiNote>(ub.url(id), note);
  }

  delete(ub: UrlBuilder, id: string, note: ApiNote): Observable<ApiNote> {
    return this.http.delete<ApiNote>(ub.url(id, note.string));
  }
}
