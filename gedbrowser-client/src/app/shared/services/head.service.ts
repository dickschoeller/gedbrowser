import {HttpClient} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {Observable} from 'rxjs/Observable';

import {ApiHead} from '../models';

@Injectable()
export class HeadService {

  constructor(private http: HttpClient) { }

  getOne(db: string, id: string): Observable<ApiHead> {
    return this.http
      .get<ApiHead>(
        'http://largo.schoellerfamily.org:9084/gedbrowser-api/v1/dbs/' + db);
  }

  put(db: string, head: ApiHead): Observable<ApiHead> {
    return this.http.put<ApiHead>(
      'http://largo.schoellerfamily.org:9084/gedbrowser-api/v1/dbs/' + db, head);
  }
}
