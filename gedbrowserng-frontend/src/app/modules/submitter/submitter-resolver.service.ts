import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, Resolve, Router, RouterStateSnapshot } from '@angular/router';
import { Observable } from 'rxjs/Observable';
import 'rxjs/add/operator/catch';

import {ApiSubmitter} from '../../models';
import {SubmitterService} from '../../services';

@Injectable()
export class SubmitterResolverService implements Resolve<ApiSubmitter> {

  constructor(
    private submitterService: SubmitterService,
    private route: Router
  ) {}

  resolve(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot
  ): Observable<ApiSubmitter> {
    const dataset: string = route.params['dataset'];
    const id: string = route.params['string'];
    const foo: Observable<ApiSubmitter> =
       this.submitterService.getOne(dataset, id);
//      .catch((err) => this.router.navigateByUrl('/'));
    return foo;
  }
}
