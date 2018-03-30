import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs/Observable';
import {ApiObject} from '../models';

@Injectable()
export abstract class SpecialPost<T extends ApiObject> {
  constructor(private http: HttpClient) {}

  abstract url(db, t, id);

  post(url, p): Observable<T> {
    return this.http.post<T>(url, p);
  }
}
