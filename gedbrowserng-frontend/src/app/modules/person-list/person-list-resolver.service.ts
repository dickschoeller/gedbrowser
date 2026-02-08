import { Injectable, Inject } from '@angular/core';
import { ActivatedRouteSnapshot, Resolve, Router, RouterStateSnapshot } from '@angular/router';
import { Observable } from 'rxjs';

import { ApiPerson } from '../../models';
import { PersonService } from '../../services';
import { ResolverHelper } from '../../utils';

@Injectable()
export class PersonListResolverService implements Resolve<Array<ApiPerson>> {
  rh: ResolverHelper<ApiPerson> = new ResolverHelper<ApiPerson>();

  constructor(@Inject(PersonService) public readonly personService: PersonService, @Inject(Router) public readonly router: Router) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<Array<ApiPerson>> {
    return this.rh.resolveAll(route, state, this.personService);
  }
}
