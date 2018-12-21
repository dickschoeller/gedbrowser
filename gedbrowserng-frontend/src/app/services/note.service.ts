import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

import { ApiNote } from '../models';
import { ServiceBase } from './service-base';

@Injectable()
export class NoteService extends ServiceBase<ApiNote>  {
  url(db): string {
    return this.baseUrl(db) + '/notes';
  }
}
