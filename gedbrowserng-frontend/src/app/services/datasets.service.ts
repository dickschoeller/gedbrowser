import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

import { UrlBuilder } from '../utils/urlbuilder';

@Injectable()
export class DatasetsService {

  constructor(private http: HttpClient) { }

  get(): Observable<Array<String>> {
    return this.http.get<Array<String>>(this.url());
  }

  url() {
    const ub = new UrlBuilder('', '', '');
    return ub.baseUrl();
  }
}
