import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { ApiSource } from '../models/api-source.model';

/**
 * Service for obtaining source objects.
 */
@Injectable()
export class SourceService {

  constructor(private http: HttpClient) {}

  getAll(db: string): Observable<Array<ApiSource>> {
    return this.http.get<Array<ApiSource>>(
      'http://localhost:8080/gedbrowser-api/dbs/' + db + '/sources');
  }

  getOne(db: string, id: string): Observable<ApiSource> {
    return this.http.get<ApiSource>(
      'http://localhost:8080/gedbrowser-api/dbs/' + db + '/sources/' + id);
  }
}
