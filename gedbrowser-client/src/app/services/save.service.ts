import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders, HttpResponse} from '@angular/common/http';
import {Observable} from 'rxjs/Observable';

import {UrlBuilder} from '../utils';
/**
 * Service for obtaining saving a db.
 */
@Injectable()
export class SaveService {
  constructor(private http: HttpClient) {}

  getTextFile(dataset: string) {
    const ub = new UrlBuilder(dataset, '', '');
    return this.http.get(
      ub.baseUrl() + '/save', {responseType: 'text'});
  }
}
