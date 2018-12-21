import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, Resolve, Router, RouterStateSnapshot } from '@angular/router';
import { Observable } from 'rxjs';

import { ApiNote } from '../../models';
import { NoteService } from '../../services';
import { ResolverHelper } from '../../utils';

@Injectable()
export class NoteResolverService implements Resolve<ApiNote> {
  rh: ResolverHelper<ApiNote> = new ResolverHelper<ApiNote>();

  constructor(private noteService: NoteService, private route: Router) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ApiNote> {
    return this.rh.resolve(route, state, this.noteService);
  }
}
