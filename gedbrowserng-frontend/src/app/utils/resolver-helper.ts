import { ActivatedRouteSnapshot, RouterStateSnapshot } from '@angular/router';
import { Observable } from 'rxjs';

export class ResolverHelper<T> {
  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot, service): Observable<T> {
    const dataset: string = route.params['dataset'];
    const id: string = route.params['string'];
    return service.getOne(dataset, id);
//      .catch((err) => this.router.navigateByUrl('/'));
  }

  resolveAll(route: ActivatedRouteSnapshot, state: RouterStateSnapshot, service): Observable<Array<T>> {
    const dataset: string = route.params['dataset'];
    return service.getAll(dataset);
//      .catch((err) => this.router.navigateByUrl('/'));
  }
}
