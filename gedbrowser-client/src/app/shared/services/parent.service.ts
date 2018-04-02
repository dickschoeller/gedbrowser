import {HttpClient} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {Observable} from 'rxjs/Observable';

import {ApiPerson} from '../models';
import {PostRelatedPerson} from './post-related-person';
import {UrlBuilder} from './urlbuilder';

@Injectable()
export class ParentService implements PostRelatedPerson {
  constructor(private http: HttpClient) {}

  post(db, id, person: ApiPerson): Observable<ApiPerson> {
    const ub = new UrlBuilder(db);
    return this.http.post<ApiPerson>(ub.url('persons', id, 'parents'), person);
  }
}
