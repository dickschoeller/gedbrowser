import { Component, OnInit, Input } from '@angular/core';

import { ApiAttribute, ApiFamily, ApiPerson, FamilyService } from '../shared';
import { Observable } from 'rxjs/Observable';

/**
 * Implements a family block within a person page.
 *
 * Inputs:
 *  string: the ID string of the family
 *  person: the person object of the containing block
 *  index: the index of the family in the order list for the containing person
 *
 * Fetches:
 *  family the family identified by the ID
 */
@Component({
  selector: 'app-person-family',
  templateUrl: './person-family.component.html',
  styleUrls: ['./person-family.component.css']
})
export class PersonFamilyComponent implements OnInit {
  @Input() string: string;
  @Input() person: ApiPerson;
  @Input() index: number;
  family: ApiFamily;

  constructor(
    private familyService: FamilyService,
  ) { }

  ngOnInit() {
    this.familyService.getOne('schoeller', this.string)
      .subscribe((family: ApiFamily) => {
        this.family = family;
    });
  }

  familyString() {
    return this.family.string;
  }

  spouse(): ApiAttribute {
    const stripped: Array<ApiAttribute> = new Array<ApiAttribute>();
    for (const attribute of this.family.attributes) {
      if (attribute.type === 'husband'
        || attribute.type === 'wife') {
        if (attribute.string !== this.person.string) {
          return attribute;
        }
      }
    }
    return null;
  }

  strippedAttributes(): Array<ApiAttribute> {
    const stripped: Array<ApiAttribute> = new Array<ApiAttribute>();
    for (const attribute of this.family.attributes) {
      if (attribute.type !== 'husband'
        && attribute.type !== 'wife'
        && attribute.type !== 'child') {
        stripped.push(attribute);
      }
    }
    return stripped;
  }


  children(): Array<ApiAttribute> {
    const stripped: Array<ApiAttribute> = new Array<ApiAttribute>();
    for (const attribute of this.family.attributes) {
      if (attribute.type === 'child') {
        stripped.push(attribute);
      }
    }
    return stripped;
  }
}
