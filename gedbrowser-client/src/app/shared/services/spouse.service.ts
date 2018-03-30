import {HttpClient} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {Observable} from 'rxjs/Observable';

import {ApiPerson} from '../models';
import {SpecialPost} from './special-post';
import {UrlBuilder} from './urlbuilder';

@Injectable()
export class SpouseService extends SpecialPost<ApiPerson> {
  postSpouseToPerson(db, id, person: ApiPerson): Observable<ApiPerson> {
    return this.post(this.url(db, 'persons', id), person);
  }

  postSpouseToFamily(db, id, person: ApiPerson): Observable<ApiPerson> {
    return this.post(this.url(db, 'families', id), person);
  }

  url(db, t, id) {
    const ub = new UrlBuilder(db);
    return ub.spousesUrl(t, id);
  }
}
