import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import {
  ApiAttribute,
  ApiPerson,
  AttributeListComponent,
  LifespanUtil,
  PersonService,
} from '../shared';

/**
 * Implements a person page.
 *
 * Fetches:
 *  person: the person routed by the module
 */
@Component({
  selector: 'app-person',
  templateUrl: './person.component.html',
  styleUrls: ['./person.component.css']
})
export class PersonComponent implements OnInit {
  person: ApiPerson;

  constructor(private route: ActivatedRoute,
    private personService: PersonService,
    private router: Router
  ) {}

  ngOnInit() {
    this.route.data.subscribe(
      (data: {person: ApiPerson}) => {
        this.person = data.person;
      }
    );
  }

  lifespanDateString() {
    return new LifespanUtil(this.person.lifespan).lifespanDateString();
  }

  famsAttributes(): Array<ApiAttribute> {
    const fams: Array<ApiAttribute> = new Array<ApiAttribute>();
    for (const attribute of this.person.attributes) {
      if (attribute.type === 'fams') {
        fams.push(attribute);
      }
    }
    return fams;
  }

  /**
   * Remove family links and the first instance of name.
   * Those will be handled elsewhere.
   */
  strippedAttributes(): Array<ApiAttribute> {
    const stripped: Array<ApiAttribute> = new Array<ApiAttribute>();
    for (const attribute of this.person.attributes) {
      if (attribute.type !== 'fams' && attribute.type !== 'famc') {
        stripped.push(attribute);
      }
    }
    return stripped;
  }
}
