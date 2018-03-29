import {HttpClient} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {Observable} from 'rxjs/Observable';

import {ApiPerson} from '../models';
import {UrlBuilder} from './urlbuilder';

@Injectable()
export class ChildService {
  constructor(private http: HttpClient) {}

  postChildToPerson(db: string, id: string, person: ApiPerson): Observable<ApiPerson> {
    const ub = new UrlBuilder(db);
    return this.http.post<ApiPerson>(ub.childrenUrl('persons', id), person);
  }

  postChildToFamily(db: string, id: string, person: ApiPerson): Observable<ApiPerson> {
    const ub = new UrlBuilder(db);
    return this.http.post<ApiPerson>(ub.childrenUrl('families', id), person);
  }
}
