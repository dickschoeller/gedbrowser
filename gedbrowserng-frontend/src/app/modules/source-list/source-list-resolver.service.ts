import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, Resolve, Router, RouterStateSnapshot } from '@angular/router';
import { Observable } from 'rxjs/Observable';

import { ApiSource } from '../../models';
import { SourceService } from '../../services';
import { ResolverHelper } from '../../utils';

@Injectable()
export class SourceListResolverService implements Resolve<Array<ApiSource>> {
  rh: ResolverHelper<ApiSource> = new ResolverHelper<ApiSource>();

  constructor(public sourceService: SourceService, public router: Router) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<Array<ApiSource>> {
    return this.rh.resolveAll(route, state, this.sourceService);
  }
}
