import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, Resolve, Router, RouterStateSnapshot } from '@angular/router';
import { Observable } from 'rxjs/Observable';
import 'rxjs/add/operator/catch';

import { ApiSubmitter, SubmitterService } from '../shared';

@Injectable()
export class SubmitterListResolver  {

  constructor(
    private submitterService: SubmitterService,
    private router: Router
  ) {}

  resolve(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot
  ): Observable<any> {
    return this.submitterService.getAll('schoeller').catch((err) => this.router.navigateByUrl('/'));
  }
}
