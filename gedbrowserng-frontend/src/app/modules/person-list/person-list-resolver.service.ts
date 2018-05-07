import {Injectable} from '@angular/core';
import {ActivatedRouteSnapshot, Resolve, Router, RouterStateSnapshot} from '@angular/router';
import {Observable} from 'rxjs/Observable';
import 'rxjs/add/operator/catch';

import {ApiPerson} from '../../models';
import {PersonService, ResolverBase} from '../../services';

@Injectable()
export class PersonListResolverService
  extends ResolverBase
  implements Resolve<Array<ApiPerson>> {

  constructor(personService: PersonService, router: Router) {
    super(personService, router);
  }
}
