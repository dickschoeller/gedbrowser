import {Component, OnInit, Input, OnChanges} from '@angular/core';

import {ApiAttribute, ApiPerson} from '../../models';
import {PersonService} from '../../services';
import {LifespanUtil, StringUtil} from '../../utils';
import { PersonGetter } from './person-getter';

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
export class PersonFamilySpouseComponent extends PersonGetter implements OnInit, OnChanges {
  @Input() dataset: string;
  @Input() attribute: ApiAttribute;
  spouse: ApiPerson;

  constructor(personService: PersonService) {
    super(personService);
  }

  ngOnInit() {
    this.init();
  }

  ngOnChanges() {
    this.init();
  }

  private init(): void {
    this.get(this.dataset, this.attribute.string, (person: ApiPerson) => {
      this.spouse = person;
    });
  }

  lifespanYearString(): string {
    return new LifespanUtil(this.spouse.lifespan).lifespanYearString();
  }
}
