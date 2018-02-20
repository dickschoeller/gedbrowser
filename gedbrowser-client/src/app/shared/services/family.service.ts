import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs/Observable';

import { ApiFamily } from '../models';

/**
 * Service for obtaining family objects.
 */
@Injectable()
export class FamilyService {

  constructor(private http: HttpClient) { }

  getOne(db: string, id: string): Observable<ApiFamily> {
    return this.http
      .get<ApiFamily>(
        'http://localhost:8080/gedbrowser-api/dbs/' + db + '/families/' + id);
  }

  put(db: string, family: ApiFamily) {
    this.http.put(
      'http://localhost:8080/gedbrowser-api/dbs/' + db + '/families/' + family.string,
      family);
  }

  post(db: string, family: ApiFamily) {
    this.http.post(
      'http://localhost:8080/gedbrowser-api/dbs/' + db + '/families', family);
  }
}
