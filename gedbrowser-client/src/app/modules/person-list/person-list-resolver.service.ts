import {Injectable} from '@angular/core';
import {ActivatedRouteSnapshot, Resolve, Router, RouterStateSnapshot} from '@angular/router';
import {Observable} from 'rxjs/Observable';
import 'rxjs/add/operator/catch';

import {ApiPerson} from '../../models';
import {PersonService} from '../../services';

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
    const dataset: string = route.params['dataset'];
    return this.personService.getAll(dataset)
      .catch((err) => this.router.navigateByUrl('/'));
  }
}
