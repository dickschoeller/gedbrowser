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

  getAll(db): Observable<Array<ApiPerson>> {
    return this.http.get<Array<ApiPerson>>(
      'http://localhost:8080/gedbrowser-api/dbs/' + db + '/persons');
  }

  getOne(db, id): Observable<ApiPerson> {
    return this.http
      .get<ApiPerson>(
        'http://localhost:8080/gedbrowser-api/dbs/' + db + '/persons/' + id);
  }
}
