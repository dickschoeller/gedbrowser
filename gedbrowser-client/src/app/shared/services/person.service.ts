import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { ApiPerson } from '../models/api-person.model';

/**
 * Service for obtaining person objects.
 */
@Injectable()
export class PersonService {

  constructor(private http: HttpClient) {}

  getAll(db: string): Observable<Array<ApiPerson>> {
    return this.http.get<Array<ApiPerson>>(
      'http://localhost:8080/gedbrowser-api/dbs/' + db + '/persons');
  }

  getOne(db: string, id: string): Observable<ApiPerson> {
    return this.http
      .get<ApiPerson>(
        'http://localhost:8080/gedbrowser-api/dbs/' + db + '/persons/' + id);
  }

  put(db: string, person: ApiPerson) {
    this.http.put(
      'http://localhost:8080/gedbrowser-api/dbs/' + db + '/persons/' + person.string,
      person);
  }

  post(db: string, person: ApiPerson) {
    this.http.post(
      'http://localhost:8080/gedbrowser-api/dbs/' + db + '/persons', person);
  }
}
