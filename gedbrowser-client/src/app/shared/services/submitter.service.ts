import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { ApiSubmitter } from '../models/api-submitter.model';

/**
 * Service for obtaining submitter objects.
 */
@Injectable()
export class SubmitterService {

  constructor(private http: HttpClient) {}

  getAll(db: string): Observable<Array<ApiSubmitter>> {
    return this.http.get<Array<ApiSubmitter>>(
      'http://localhost:8080/gedbrowser-api/dbs/' + db + '/submitters');
  }

  getOne(db: string, id: string): Observable<ApiSubmitter> {
    return this.http.get<ApiSubmitter>(
      'http://localhost:8080/gedbrowser-api/dbs/' + db + '/submitters/' + id);
  }

  put(db: string, submitter: ApiSubmitter) {
    this.http.put(
      'http://localhost:8080/gedbrowser-api/dbs/' + db + '/submitters/' + submitter.string,
      submitter);
  }

  post(db: string, submitter: ApiSubmitter) {
    this.http.post(
      'http://localhost:8080/gedbrowser-api/dbs/' + db + '/submitters', submitter);
  }
}
