import { Observable } from 'rxjs/Observable';

import { ApiSubmitter } from '../models';
import { UrlBuilder } from '../utils/urlbuilder';

export interface PostRelatedSubmitter {
  p(ub: UrlBuilder, id: string, submitter: ApiSubmitter): Observable<ApiSubmitter>;
}
