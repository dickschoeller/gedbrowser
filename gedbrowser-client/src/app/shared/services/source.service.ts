import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs/Observable';

import {ApiSource} from '../models/api-source.model';
import {ApiService} from './api-service';

/**
 * Service for obtaining source objects.
 */
@Injectable()
export class SourceService implements ApiService<ApiSource> {

  constructor(private http: HttpClient) {}

  getAll(db: string): Observable<Array<ApiSource>> {
    return this.http.get<Array<ApiSource>>(
      'http://largo.schoellerfamily.org:8080/gedbrowser-api/dbs/' + db + '/sources');
  }

  getOne(db: string, id: string): Observable<ApiSource> {
    return this.http.get<ApiSource>(
      'http://largo.schoellerfamily.org:8080/gedbrowser-api/dbs/' + db + '/sources/' + id);
  }

  put(db: string, source: ApiSource): Observable<ApiSource> {
    return this.http.put<ApiSource>(
      'http://largo.schoellerfamily.org:8080/gedbrowser-api/dbs/' + db + '/sources/' + source.string,
      source);
  }

  post(db: string, source: ApiSource): Observable<ApiSource> {
    return this.http.post<ApiSource>(
      'http://largo.schoellerfamily.org:8080/gedbrowser-api/dbs/' + db + '/sources', source);
  }

  delete(db: string, source: ApiSource): Observable<ApiSource> {
    return this.http.delete<ApiSource>(
      'http://largo.schoellerfamily.org:8080/gedbrowser-api/dbs/' + db + '/sources/' + source.string);
  }
}
