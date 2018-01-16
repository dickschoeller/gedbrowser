import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';

import {ApiPerson, PersonService} from '../shared';

@Component({
  selector: 'app-person-list',
  templateUrl: './person-list.component.html',
  styleUrls: ['./person-list.component.css']
})
export class PersonListComponent implements OnInit {
  persons: ApiPerson[];

  constructor(private route: ActivatedRoute,
    private personService: PersonService,
    private router: Router
  ) {}

  /**
   * Comparison function to sort the persons returned.
   * Index name is the first level sort.
   * If those are the same, then by ID.
   */
  compare = function(a: ApiPerson, b: ApiPerson) {
    const val = a.indexName.localeCompare(b.indexName);
    if (val !== 0) {
      return val;
    }
    return a.string.localeCompare(b.string);
  };

  /**
   * Prepare the page display.
   *
   * Read persons from the web service and sort using our comparator.
   */
  ngOnInit() {
    this.route.data.subscribe(
      (data: {persons: ApiPerson[]}) => {
        this.persons = data.persons;
        this.persons.sort(this.compare);
      }
    );
  }
}
