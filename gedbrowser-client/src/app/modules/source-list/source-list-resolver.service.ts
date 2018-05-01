import {Injectable} from '@angular/core';
import {ActivatedRouteSnapshot, Resolve, Router, RouterStateSnapshot} from '@angular/router';
import {Observable} from 'rxjs/Observable';
import 'rxjs/add/operator/catch';

import {ApiSource} from '../../models';
import {SourceService} from '../../services';

@Injectable()
export class SourceListResolver  {

  constructor(
    private sourceService: SourceService,
    private router: Router
  ) {}

  resolve(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot
  ): Observable<any> {
    const dataset: string = route.params['dataset'];
    return this.sourceService.getAll(dataset).catch((err) => this.router.navigateByUrl('/'));
  }
}
