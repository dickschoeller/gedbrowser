import {Component, OnInit, Input} from '@angular/core';
import {ApiAttribute, ApiPerson, PersonService, LifespanUtil} from '../shared';

/**
 * Implements a child block within a family on a person page.
 *
 * Inputs:
 *  child: the attribute referring to the child
 *
 * Fetches:
 *  person: the person identified by the attribute
 */
@Component({
  selector: 'app-person-family-child',
  templateUrl: './person-family-child.component.html',
  styleUrls: ['./person-family-child.component.css']
})
export class PersonFamilyChildComponent implements OnInit {
  @Input() child: ApiAttribute;
  person: ApiPerson;

  constructor(
    private personService: PersonService,
  ) { }

  ngOnInit() {
    this.personService.getOne('schoeller', this.child.string)
    .subscribe((person: ApiPerson) => {
      this.person = person;
    });
  }

  lifespanYearString(): string {
    return new LifespanUtil(this.person.lifespan).lifespanYearString();
  }
}
