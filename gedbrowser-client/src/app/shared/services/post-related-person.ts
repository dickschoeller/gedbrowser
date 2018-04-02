import {ApiPerson} from '../models';
import {Observable} from 'rxjs/Observable';

export interface PostRelatedPerson {
    post(db: string, id: string, person: ApiPerson): Observable<ApiPerson>;
}
