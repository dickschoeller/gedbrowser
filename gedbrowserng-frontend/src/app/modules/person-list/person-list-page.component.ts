import { Component, OnInit, OnChanges , Inject } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { ApiPerson } from '../../models';
import { PersonService } from '../../services';
import { ApiComparators, NewPersonHelper } from '../../utils';
import { PersonListComponent } from './person-list.component';

@Component({
    selector: 'app-person-list-page',
    standalone: true,
    template: `<app-person-list [p]="this" [dataset]="dataset" [persons]="persons"></app-person-list>`,
    styles: [],
    imports: [PersonListComponent]
})
export class PersonListPageComponent implements OnInit, OnChanges {
  dataset: string;
  persons: ApiPerson[];

  constructor(@Inject(ActivatedRoute) private route: ActivatedRoute,
    @Inject(PersonService) private personService: PersonService,
    @Inject(Router) private router: Router) { }

  ngOnInit(): void {
    this.init();
  }

  ngOnChanges(): void {
    this.init();
  }

  private init(): void {
    this.route.params.subscribe((params) => {
      this.dataset = params['dataset'];
    });
    this.route.data.subscribe(
      (data: {dataset: string, persons: ApiPerson[]}) => {
        // Guard against undefined resolver data
        this.persons = data && data.persons ? data.persons : [];
        if (this.persons && this.persons.length > 0) {
          this.persons.sort(ApiComparators.comparePersons);
        }
      }
    );
  }

  refreshPerson(): void {
    this.router.routeReuseStrategy.shouldReuseRoute = function() {
      return false;
    };

    const currentUrl = this.router.url + '?';

    this.personService.getAll(this.dataset).subscribe(
      (persons: Array<ApiPerson>) => {
        this.persons = persons && persons.sort ? persons.sort(ApiComparators.comparePersons) : [];
      }
    );
  }
}