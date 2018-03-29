import {HttpClient} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {Observable} from 'rxjs/Observable';

import {ApiPerson} from '../models';
import {UrlBuilder} from './urlbuilder';

@Injectable()
export class SpouseService {
  constructor(private http: HttpClient) {}

  postSpouseToPerson(db: string, id: string, person: ApiPerson): Observable<ApiPerson> {
    const ub = new UrlBuilder(db);
    return this.http.post<ApiPerson>(ub.spousesUrl('persons', id), person);
  }

  postSpouseToFamily(db: string, id: string, person: ApiPerson): Observable<ApiPerson> {
    const ub = new UrlBuilder(db);
    return this.http.post<ApiPerson>(ub.spousesUrl('families', id), person);
  }
}
