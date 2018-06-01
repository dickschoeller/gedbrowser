import {Observable} from 'rxjs/Observable';

import {ApiPerson} from '../models';

import {UrlBuilder} from '../utils/urlbuilder';

export interface PostRelatedPerson {
    post(ub: UrlBuilder, id: string, person: ApiPerson): Observable<ApiPerson>;
    put(ub: UrlBuilder, id: string, person: ApiPerson): Observable<ApiPerson>;
}
