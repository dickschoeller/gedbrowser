import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, Resolve, Router, RouterStateSnapshot } from '@angular/router';
import { Observable } from 'rxjs/Observable';

import { ApiSubmitter } from '../../models';
import { SubmitterService } from '../../services';
import { ResolverHelper } from '../../utils';

@Injectable()
export class SubmitterResolverService implements Resolve<ApiSubmitter> {
  rh: ResolverHelper<ApiSubmitter> = new ResolverHelper<ApiSubmitter>();

  constructor(private submitterService: SubmitterService, private route: Router) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ApiSubmitter> {
    return this.rh.resolve(route, state, this.submitterService);
  }
}
