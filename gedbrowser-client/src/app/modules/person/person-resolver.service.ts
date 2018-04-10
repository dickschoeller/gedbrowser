import {Injectable} from '@angular/core';
import {ActivatedRouteSnapshot, Resolve, Router, RouterStateSnapshot} from '@angular/router';
import {Observable} from 'rxjs/Observable';
import 'rxjs/add/operator/catch';

import {ApiPerson} from '../../models';
import {PersonService} from '../../services';

/**
 * Resolves and fetches a person from the module routing.
 */
@Injectable()
export class PersonResolver implements Resolve<ApiPerson> {

  constructor(
    private personService: PersonService,
    private router: Router
  ) {}

  resolve(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot
  ): Observable<ApiPerson> {
    const id: string = route.params['string'];
    const foo: Observable<ApiPerson> =
       this.personService.getOne('schoeller', id);
//      .catch((err) => this.router.navigateByUrl('/'));
    return foo;
  }
}