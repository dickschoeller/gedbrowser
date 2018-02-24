import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs/Observable';

import {ApiSubmitter} from '../models/api-submitter.model';
import {ApiService} from './api-service';

/**
 * Service for obtaining submitter objects.
 */
@Injectable()
export class SubmitterService implements ApiService<ApiSubmitter> {

  constructor(private http: HttpClient) {}

  getAll(db: string): Observable<Array<ApiSubmitter>> {
    return this.http.get<Array<ApiSubmitter>>(
      'http://localhost:8080/gedbrowser-api/dbs/' + db + '/submitters');
  }

  getOne(db: string, id: string): Observable<ApiSubmitter> {
    return this.http.get<ApiSubmitter>(
      'http://largo.schoellerfamily.org:8080/gedbrowser-api/dbs/' + db + '/submitters/' + id);
  }

  put(db: string, submitter: ApiSubmitter): Observable<ApiSubmitter> {
    return this.http.put<ApiSubmitter>(
      'http://largo.schoellerfamily.org:8080/gedbrowser-api/dbs/' + db + '/submitters/' + submitter.string,
      submitter);
  }

  post(db: string, submitter: ApiSubmitter): Observable<ApiSubmitter> {
    return this.http.post<ApiSubmitter>(
      'http://largo.schoellerfamily.org:8080/gedbrowser-api/dbs/' + db + '/submitters', submitter);
  }

  delete(db: string, submitter: ApiSubmitter): Observable<ApiSubmitter> {
    return this.http.delete<ApiSubmitter>(
      'http://largo.schoellerfamily.org:8080/gedbrowser-api/dbs/' + db + '/submitters/' + submitter.string);
  }
}
