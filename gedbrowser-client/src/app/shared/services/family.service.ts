import {HttpClient} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {Observable} from 'rxjs/Observable';

import {ApiFamily} from '../models';
import {ApiService} from './api-service';

/**
 * Service for obtaining family objects.
 */
@Injectable()
export class FamilyService implements ApiService<ApiFamily> {

  constructor(private http: HttpClient) { }

  getAll(db: string): Observable<Array<ApiFamily>> {
    return this.http.get<Array<ApiFamily>>(
      'http://largo.schoellerfamily.org:9084/gedbrowser-api/v1/dbs/' + db + '/families');
  }

  getOne(db: string, id: string): Observable<ApiFamily> {
    return this.http
      .get<ApiFamily>(
        'http://largo.schoellerfamily.org:9084/gedbrowser-api/v1/dbs/' + db + '/families/' + id);
  }

  put(db: string, family: ApiFamily): Observable<ApiFamily> {
    return this.http.put<ApiFamily>(
      'http://largo.schoellerfamily.org:9084/gedbrowser-api/v1/dbs/' + db + '/families/' + family.string,
      family);
  }

  post(db: string, family: ApiFamily): Observable<ApiFamily> {
    return this.http.post<ApiFamily>(
      'http://largo.schoellerfamily.org:9084/gedbrowser-api/v1/dbs/' + db + '/families', family);
  }

  delete(db: string, family: ApiFamily): Observable<ApiFamily> {
    return this.http.delete<ApiFamily>(
      'http://largo.schoellerfamily.org:9084/gedbrowser-api/v1/dbs/' + db + '/families/' + family.string);
  }
}
