import { Component, OnInit, Input } from '@angular/core';
import { ApiAttribute, ApiPerson, PersonService, LifespanUtil, StringUtil } from '../shared';

/**
 * Implements a spouse block within a family on a person page.
 *
 * Inputs:
 *  attribute: the attribute referring to the spouse
 *
 * Fetches:
 *  spouse: the person identified by the attribute
 */
@Component({
  selector: 'app-person-family-spouse',
  templateUrl: './person-family-spouse.component.html',
  styleUrls: ['./person-family-spouse.component.css']
})
export class PersonFamilySpouseComponent implements OnInit {
  @Input() attribute: ApiAttribute;
  spouse: ApiPerson;

  constructor(
    private personService: PersonService,
  ) {}

  ngOnInit() {
    this.personService.getOne('schoeller', this.attribute.string)
      .subscribe((person: ApiPerson) => {
        this.spouse = person;
    });
  }

  lifespanYearString(): string {
    return new LifespanUtil(this.spouse.lifespan).lifespanYearString();
  }

  label(): string {
    const su = new StringUtil();
    return su.capitalize(this.attribute.type);
  }
}
