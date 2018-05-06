import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, Resolve, Router, RouterStateSnapshot } from '@angular/router';
import { Observable } from 'rxjs/Observable';
import 'rxjs/add/operator/catch';

import {ApiSource} from '../../models';
import {SourceService} from '../../services';

@Injectable()
export class SourceResolverService implements Resolve<ApiSource> {

  constructor(
    private sourceService: SourceService,
    private router: Router
  ) {}


  resolve(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot
  ): Observable<ApiSource> {
    const dataset: string = route.params['dataset'];
    const id: string = route.params['string'];
    const foo: Observable<ApiSource> =
       this.sourceService.getOne(dataset, id);
//      .catch((err) => this.router.navigateByUrl('/'));
    return foo;
  }
}
