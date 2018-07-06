import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, Resolve, Router, RouterStateSnapshot } from '@angular/router';
import { Observable } from 'rxjs/Observable';

import { ApiHead } from '../../models';
import { HeadService } from '../../services';
import { ResolverHelper } from '../../utils';

@Injectable()
export class HeadResolverService implements Resolve<ApiHead> {
  rh: ResolverHelper<ApiHead> = new ResolverHelper<ApiHead>();

  constructor(private headService: HeadService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ApiHead> {
    return this.rh.resolve(route, state, this.headService);
  }
}
