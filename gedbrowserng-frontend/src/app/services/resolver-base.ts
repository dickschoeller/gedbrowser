import {Router, ActivatedRouteSnapshot, RouterStateSnapshot} from '@angular/router';
import {ServiceBase} from './service-base';
import {Observable} from 'rxjs/Observable';
import 'rxjs/add/operator/catch';

export class ResolverBase {
  constructor(private service: ServiceBase<any>,
    private router: Router) {}

  resolve(route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot): Observable<any> {
    const dataset: string = route.params['dataset'];
    return this.service.getAll(dataset)
      .catch((err) => this.router.navigateByUrl('/'));
  }
}
