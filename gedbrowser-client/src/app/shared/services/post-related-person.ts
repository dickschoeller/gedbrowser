import {Observable} from 'rxjs/Observable';
import {ApiPerson} from '../models';
import {UrlBuilder} from './urlbuilder';

export interface PostRelatedPerson {
    p(ub: UrlBuilder, id: string, person: ApiPerson): Observable<ApiPerson>;
}
