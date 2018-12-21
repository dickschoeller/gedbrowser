import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, Resolve, Router, RouterStateSnapshot } from '@angular/router';
import { Observable } from 'rxjs';

import { ApiPerson } from '../../models';
import { PersonService } from '../../services';
import { ResolverHelper } from '../../utils';

@Injectable()
export class PersonListResolverService implements Resolve<Array<ApiPerson>> {
  rh: ResolverHelper<ApiPerson> = new ResolverHelper<ApiPerson>();

  constructor(public personService: PersonService, public router: Router) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<Array<ApiPerson>> {
    return this.rh.resolveAll(route, state, this.personService);
  }
}
