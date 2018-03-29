import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders, HttpResponse} from '@angular/common/http';
import {Observable} from 'rxjs/Observable';

/**
 * Service for obtaining saving a db.
 */
@Injectable()
export class SaveService {
  constructor(private http: HttpClient) {}

  getTextFile(db: string) {
    return this.http.get(
      'http://largo.schoellerfamily.org:9084/gedbrowser-api/v1/dbs/' + db + '/save', {responseType: 'text'});
  }
}
