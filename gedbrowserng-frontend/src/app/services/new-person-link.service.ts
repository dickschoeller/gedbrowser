import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { ApiPerson } from '../models';
import { UrlBuilder } from '../utils';

import { CrudRelated } from './crud-related';

@Injectable()
export class NewPersonLinkService implements CrudRelated<ApiPerson> {
  constructor(private http: HttpClient) {}

  post(ub: UrlBuilder, id: string, person: ApiPerson): Observable<ApiPerson> {
    return this.http.post<ApiPerson>(ub.url(id), person);
  }

  put(ub: UrlBuilder, id: string, person: ApiPerson): Observable<ApiPerson> {
    return this.http.put<ApiPerson>(ub.url(id), person);
  }

  delete(ub: UrlBuilder, id: string, person: ApiPerson): Observable<ApiPerson> {
    return this.http.delete<ApiPerson>(ub.url(id, person.string));
  }
}
