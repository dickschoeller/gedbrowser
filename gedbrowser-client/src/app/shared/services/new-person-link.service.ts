import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs/Observable';

import {ApiPerson} from '../models';
import {PostRelatedPerson} from './post-related-person';
import {UrlBuilder} from './urlbuilder';

@Injectable()
export class NewPersonLinkService implements PostRelatedPerson {
  constructor(private http: HttpClient) {}

  p(ub: UrlBuilder, id: string, person: ApiPerson): Observable<ApiPerson> {
    return this.http.post<ApiPerson>(ub.url(id), person);
  }
}
