import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, Resolve, Router, RouterStateSnapshot } from '@angular/router';
import { Observable } from 'rxjs';

import { ApiNote } from '../../models';
import { NoteService } from '../../services';
import { ResolverHelper } from '../../utils';

@Injectable()
export class NoteListResolverService implements Resolve<Array<ApiNote>> {
  rh: ResolverHelper<ApiNote> = new ResolverHelper<ApiNote>();

  constructor(public noteService: NoteService, public router: Router) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<Array<ApiNote>> {
    return this.rh.resolveAll(route, state, this.noteService);
  }
}
