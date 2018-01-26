import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, Resolve, Router, RouterStateSnapshot } from '@angular/router';
import { Observable } from 'rxjs/Observable';
import 'rxjs/add/operator/catch';

import { ApiSource, SourceService } from '../shared';

@Injectable()
export class SourceResolver implements Resolve<ApiSource> {

  constructor(
    private sourceService: SourceService,
    private router: Router
  ) {}


  resolve(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot
  ): Observable<ApiSource> {
    const id: string = route.params['string'];
    const foo: Observable<ApiSource> =
       this.sourceService.getOne('schoeller', id);
//      .catch((err) => this.router.navigateByUrl('/'));
    return foo;
  }
}
