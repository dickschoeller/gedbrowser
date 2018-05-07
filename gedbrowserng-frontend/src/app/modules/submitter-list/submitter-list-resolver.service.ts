import {Injectable} from '@angular/core';
import {ActivatedRouteSnapshot, Resolve, Router, RouterStateSnapshot} from '@angular/router';
import {Observable} from 'rxjs/Observable';
import 'rxjs/add/operator/catch';

import {ApiSubmitter} from '../../models';
import {SubmitterService, ResolverBase} from '../../services';

@Injectable()
export class SubmitterListResolverService
  extends ResolverBase
  implements Resolve<Array<ApiSubmitter>> {

  constructor(submitterService: SubmitterService, router: Router) {
    super(submitterService, router);
  }
}
