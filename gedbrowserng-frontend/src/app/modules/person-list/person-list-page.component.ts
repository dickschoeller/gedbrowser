import { Component, OnInit, OnChanges } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { ApiPerson } from '../../models';
import { PersonService } from '../../services';
import { ApiComparators, NewPersonHelper } from '../../utils';

@Component({
  selector: 'app-person-list-page',
  templateUrl: './person-list-page.component.html',
  styleUrls: ['./person-list-page.component.css']
})
export class PersonListPageComponent implements OnInit, OnChanges {
  dataset: string;
  persons: ApiPerson[];

  constructor(private route: ActivatedRoute,
    private personService: PersonService,
    private router: Router) { }

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
        const comparator: ApiComparators = new ApiComparators();
        this.persons = data.persons;
        this.persons.sort(comparator.comparePersons);
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
        const comparator: ApiComparators = new ApiComparators();
        this.persons = persons.sort(comparator.comparePersons);
      }
    );
  }
}
