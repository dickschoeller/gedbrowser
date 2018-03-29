import {HttpClient} from '@angular/common/http';
import {ApiObject} from '../models';
import {Observable} from 'rxjs/Observable';

export abstract class SpecialPost<T extends ApiObject> {
  constructor(private http: HttpClient) {}

  abstract url(db, t, id);

  post(url, p): Observable<T> {
    return this.http.post<T>(url, p);
  }
}
