import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, Resolve, Router, RouterStateSnapshot } from '@angular/router';
import { Observable } from 'rxjs/Observable';

import { ApiPerson } from '../../models';
import { PersonService } from '../../services';
import { ResolverHelper } from '../../utils';

/**
 * Resolves and fetches a person from the module routing.
 */
@Injectable()
export class PersonResolverService implements Resolve<ApiPerson> {
  rh: ResolverHelper<ApiPerson> = new ResolverHelper<ApiPerson>();

  constructor(private personService: PersonService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ApiPerson> {
    return this.rh.resolve(route, state, this.personService);
  }
}
