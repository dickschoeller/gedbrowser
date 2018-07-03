import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { ApiSubmitter } from '../models';
import { UrlBuilder } from '../utils/urlbuilder';

import { PostRelatedSubmitter } from './post-related-submitter';

@Injectable()
export class NewSubmitterLinkService implements PostRelatedSubmitter {
  constructor(private http: HttpClient) {}

  p(ub: UrlBuilder, id: string, submitter: ApiSubmitter): Observable<ApiSubmitter> {
    return this.http.post<ApiSubmitter>(ub.url(id), submitter);
  }
}
