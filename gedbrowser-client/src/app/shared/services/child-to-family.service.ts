import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs/Observable';

import {ApiPerson} from '../models';
import { PostRelatedPerson } from './post-related-person';
import {UrlBuilder} from './urlbuilder';

@Injectable()
export class ChildToFamilyService implements PostRelatedPerson {
  constructor(private http: HttpClient) {}

  post(db: string, id: string, person: ApiPerson): Observable<ApiPerson> {
    const ub = new UrlBuilder(db);
    return this.http.post<ApiPerson>(ub.url('families', id, 'children'), person);
  }
}
