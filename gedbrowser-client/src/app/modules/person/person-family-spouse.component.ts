import {Component, OnInit, Input, OnChanges} from '@angular/core';

import {ApiAttribute, ApiPerson} from '../../models';
import {PersonService} from '../../services';
import {LifespanUtil, StringUtil} from '../../utils';

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
export class PersonFamilySpouseComponent implements OnInit, OnChanges {
  @Input() attribute: ApiAttribute;
  spouse: ApiPerson;

  constructor(
    private personService: PersonService,
  ) {}

  ngOnInit() {
    this.init();
  }

  ngOnChanges() {
    this.init();
  }

  private init(): void {
    this.personService.getOne('schoeller', this.attribute.string)
      .subscribe((person: ApiPerson) => {
        this.spouse = person;
    });
  }

  lifespanYearString(): string {
    return new LifespanUtil(this.spouse.lifespan).lifespanYearString();
  }
}
