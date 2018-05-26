import {Observable} from 'rxjs/Observable';

import {ApiSource} from '../models';

import {UrlBuilder} from '../utils/urlbuilder';

export interface PostRelatedSource {
    p(ub: UrlBuilder, id: string, source: ApiSource): Observable<ApiSource>;
}
