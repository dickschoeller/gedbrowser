import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, Resolve, Router, RouterStateSnapshot } from '@angular/router';
import { Observable } from 'rxjs/Observable';
import 'rxjs/add/operator/catch';

import { ApiPerson, PersonService } from '../shared';

@Injectable()
export class PersonListResolver implements Resolve<Array<ApiPerson>> {

  constructor(
    private personService: PersonService,
    private router: Router
  ) {}

  resolve(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot
  ): Observable<any> {
    return this.personService.getAll('schoeller')
      .catch((err) => this.router.navigateByUrl('/'));
  }
}
