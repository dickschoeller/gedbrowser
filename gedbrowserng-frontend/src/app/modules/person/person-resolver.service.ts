import { Injectable, Inject } from '@angular/core';
import { ActivatedRouteSnapshot, Resolve, Router, RouterStateSnapshot } from '@angular/router';
import { Observable } from 'rxjs';

import { ApiPerson } from '../../models';
import { PersonService } from '../../services';
import { ResolverHelper } from '../../utils';

/**
 * Resolves and fetches a person from the module routing.
 */
@Injectable()
export class PersonResolverService implements Resolve<ApiPerson> {
  rh: ResolverHelper<ApiPerson> = new ResolverHelper<ApiPerson>();

  constructor(@Inject(PersonService) private readonly personService: PersonService, @Inject(Router) private readonly router: Router) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ApiPerson> {
    return this.rh.resolve(route, state, this.personService);
  }
}
