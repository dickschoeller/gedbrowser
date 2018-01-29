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

  getOne(db, id): Observable<ApiFamily> {
    return this.http
      .get<ApiFamily>(
        'http://localhost:8080/gedbrowser-api/dbs/' + db + '/families/' + id);
  }
}
