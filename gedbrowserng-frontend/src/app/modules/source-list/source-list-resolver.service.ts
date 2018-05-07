import {Injectable} from '@angular/core';
import {ActivatedRouteSnapshot, Resolve, Router, RouterStateSnapshot} from '@angular/router';
import {Observable} from 'rxjs/Observable';
import 'rxjs/add/operator/catch';

import {ApiSource} from '../../models';
import {SourceService, ResolverBase} from '../../services';

@Injectable()
export class SourceListResolverService
  extends ResolverBase
  implements Resolve<Array<ApiSource>> {

  constructor(sourceService: SourceService, router: Router) {
    super(sourceService, router);
  }
}
