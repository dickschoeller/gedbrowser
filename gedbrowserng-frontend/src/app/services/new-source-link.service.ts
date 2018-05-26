import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs/Observable';

import {ApiSource} from '../models';

import {PostRelatedSource} from './post-related-source';
import {UrlBuilder} from '../utils/urlbuilder';

@Injectable()
export class NewSourceLinkService implements PostRelatedSource {
  constructor(private http: HttpClient) {}

  p(ub: UrlBuilder, id: string, source: ApiSource): Observable<ApiSource> {
    return this.http.post<ApiSource>(ub.url(id), source);
  }
}
