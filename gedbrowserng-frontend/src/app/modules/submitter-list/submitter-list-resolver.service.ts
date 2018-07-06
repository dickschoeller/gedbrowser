import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, Resolve, Router, RouterStateSnapshot } from '@angular/router';
import { Observable } from 'rxjs/Observable';
import 'rxjs/add/operator/catch';

import { ApiSubmitter } from '../../models';
import { SubmitterService } from '../../services';
import { ResolverHelper } from '../../utils';

@Injectable()
export class SubmitterListResolverService implements Resolve<Array<ApiSubmitter>> {
  rh: ResolverHelper<ApiSubmitter> = new ResolverHelper<ApiSubmitter>();

  constructor(public submitterService: SubmitterService, public router: Router) {
  }

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<Array<ApiSubmitter>> {
    return this.rh.resolveAll(route, state, this.submitterService);
  }
}
