import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, Resolve, Router, RouterStateSnapshot } from '@angular/router';
import { Observable } from 'rxjs/Observable';
import 'rxjs/add/operator/catch';

import { ApiSource, SourceService } from '../shared';

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
    return this.sourceService.getAll('schoeller').catch((err) => this.router.navigateByUrl('/'));
  }
}
