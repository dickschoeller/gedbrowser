import {HttpClient} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {Observable} from 'rxjs/Observable';

import {ApiPerson} from '../models';

@Injectable()
export class SpouseService {
  constructor(private http: HttpClient) {}

  postToPerson(db: string, id: string, person: ApiPerson): Observable<ApiPerson> {
    return this.http.post<ApiPerson>(
      'http://largo.schoellerfamily.org:9084/gedbrowser-api/v1/dbs/' + db + '/persons/' + id + '/spouses', person);
  }

  postToFamily(db: string, id: string, person: ApiPerson): Observable<ApiPerson> {
    return this.http.post<ApiPerson>(
      'http://largo.schoellerfamily.org:9084/gedbrowser-api/v1/dbs/' + db + '/families/' + id + '/spouses', person);
  }
}
