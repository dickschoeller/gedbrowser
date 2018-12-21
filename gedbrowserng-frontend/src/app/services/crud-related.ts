import { Observable } from 'rxjs/';

import { ApiObject } from '../models';
import { UrlBuilder } from '../utils';

export interface CrudRelated<T extends ApiObject> {
  post(ub: UrlBuilder, id: string, o: T): Observable<T>;
  put(ub: UrlBuilder, id: string, o: T): Observable<T>;
  delete(ub: UrlBuilder, id: string, o: T): Observable<T>;
}
