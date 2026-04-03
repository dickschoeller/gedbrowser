import { Injectable, Inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';

import { ApiNote } from '../models';
import { ServiceBase } from './service-base';

@Injectable()
export class NoteService extends ServiceBase<ApiNote>  {
  constructor(@Inject(HttpClient) http: HttpClient) {
    super(http);
  }

  url(db): string {
    return this.baseUrl(db) + '/notes';
  }
}
