import { Observable } from 'rxjs';

import { ApiObject } from '../models';

/**
 * Standard CRUD operations for our API.
 */
export interface ApiService<T extends ApiObject> {
  post(db: string, item: T): Observable<T>;

  getAll(db: string): Observable<Array<T>>;

  getOne(db: string, id: string): Observable<T>;

  put(db: string, item: T): Observable<T>;

  delete(db: string, item: T): Observable<T>;
}
