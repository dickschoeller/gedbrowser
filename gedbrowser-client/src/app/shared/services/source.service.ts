import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

@Injectable()
export class SourceService {

  constructor(private http: HttpClient) {}

  getAll(db): Observable<any> {
    return this.http.get('http://localhost:8080/gedbrowser-api/dbs/' + db + '/sources');
  }

  getOne(db, id): Observable<any> {
    return this.http
      .get('http://localhost:8080/gedbrowser-api/dbs/' + db + '/sources/' + id);
  }
}
