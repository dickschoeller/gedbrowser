import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, Resolve, Router, RouterStateSnapshot } from '@angular/router';
import { Observable } from 'rxjs/Observable';

import { ApiSource } from '../../models';
import { SourceService } from '../../services';
import { ResolverHelper } from '../../utils';

@Injectable()
export class SourceResolverService implements Resolve<ApiSource> {
  rh: ResolverHelper<ApiSource> = new ResolverHelper<ApiSource>();

  constructor(private sourceService: SourceService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ApiSource> {
    return this.rh.resolve(route, state, this.sourceService);
  }
}
