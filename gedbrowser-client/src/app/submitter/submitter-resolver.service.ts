import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, Resolve, Router, RouterStateSnapshot } from '@angular/router';
import { Observable } from 'rxjs/Observable';
import 'rxjs/add/operator/catch';

import { ApiSubmitter, SubmitterService } from '../shared';

@Injectable()
export class SubmitterResolver implements Resolve<ApiSubmitter> {

  constructor(
    private submitterService: SubmitterService,
    private route: Router
  ) {}

  resolve(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot
  ): Observable<ApiSubmitter> {
    const id: string = route.params['string'];
    const foo: Observable<ApiSubmitter> =
       this.submitterService.getOne('schoeller', id);
//      .catch((err) => this.router.navigateByUrl('/'));
    return foo;
  }
}
