import { HttpClient } from '@angular/common/http';
import { Inject, Injectable } from '@angular/core';
import { Observable, map } from 'rxjs';

import { UrlBuilder } from '../utils/urlbuilder';

@Injectable()
export class DatasetsService {

  constructor(@Inject(HttpClient) private readonly http: HttpClient) { }

  get(): Observable<Array<string>> {
    return this.http.get<Array<string>>(this.url()).pipe(
      map((datasets) => Array.from(new Set(datasets)))
    );
  }

  url() {
    const ub = new UrlBuilder('', '', '');
    return ub.baseUrl();
  }
}
