import { Component, OnInit, OnChanges , Inject } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ApiPerson } from '../../models';
import { PersonService } from '../../services';
import { ApiComparators } from '../../utils';
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

  constructor(@Inject(ActivatedRoute) private readonly route: ActivatedRoute,
    @Inject(PersonService) private readonly personService: PersonService) { }

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
        this.persons = data?.persons ?? [];
        if ((this.persons?.length ?? 0) > 0) {
          this.persons = this.persons.toSorted(ApiComparators.comparePersons);
        }
      }
    );
  }

  refreshPerson(): void {
    this.personService.getAll(this.dataset).subscribe(
      (persons: Array<ApiPerson>) => {
        if (!persons) {
          this.persons = [];
          return;
        }

        if (persons.toSorted) {
          this.persons = persons.toSorted(ApiComparators.comparePersons);
          return;
        }

        this.persons = [...persons].sort(ApiComparators.comparePersons);
      }
    );
  }
}