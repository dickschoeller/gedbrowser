import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, Resolve, Router, RouterStateSnapshot } from '@angular/router';
import { Observable } from 'rxjs/Observable';
import 'rxjs/add/operator/catch';

import { ApiHead } from '../../models';
import { HeadService } from '../../services';

@Injectable()
export class HeadResolverService implements Resolve<ApiHead> {

  constructor(
    private headService: HeadService,
    private router: Router
  ) {}


  resolve(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot
  ): Observable<ApiHead> {
    const dataset: string = route.params['dataset'];
    const foo: Observable<ApiHead> =
       this.headService.getOne(dataset);
//      .catch((err) => this.router.navigateByUrl('/'));
    return foo;
  }
}
