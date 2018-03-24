import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs/Observable';

import {ApiPerson} from '../models/api-person.model';
import {ApiService} from './api-service';

/**
 * Service for obtaining person objects.
 */
@Injectable()
export class PersonService implements ApiService<ApiPerson> {
  constructor(private http: HttpClient) {}

  getAll(db: string): Observable<Array<ApiPerson>> {
    return this.http.get<Array<ApiPerson>>(
      'http://largo.schoellerfamily.org:9084/gedbrowser-api/v1/dbs/' + db + '/persons');
  }

  getOne(db: string, id: string): Observable<ApiPerson> {
    return this.http
      .get<ApiPerson>(
        'http://largo.schoellerfamily.org:9084/gedbrowser-api/v1/dbs/' + db + '/persons/' + id);
  }

  put(db: string, person: ApiPerson): Observable<ApiPerson> {
    return this.http.put<ApiPerson>(
      'http://largo.schoellerfamily.org:9084/gedbrowser-api/v1/dbs/' + db + '/persons/' + person.string,
      person);
  }

  post(db: string, person: ApiPerson): Observable<ApiPerson> {
    return this.http.post<ApiPerson>(
      'http://largo.schoellerfamily.org:9084/gedbrowser-api/v1/dbs/' + db + '/persons', person);
  }

  delete(db: string, person: ApiPerson): Observable<ApiPerson> {
    return this.http.delete<ApiPerson>(
      'http://largo.schoellerfamily.org:9084/gedbrowser-api/v1/dbs/' + db + '/persons/' + person.string);
  }
}
