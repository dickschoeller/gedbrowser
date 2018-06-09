import {Component, OnInit, Input, OnChanges} from '@angular/core';

import {ApiAttribute, ApiPerson} from '../../models';
import {PersonService, NewPersonLinkService} from '../../services';
import {LifespanUtil, StringUtil, UrlBuilder} from '../../utils';
import {PersonFamilyComponent} from './person-family.component';
import {PersonGetter} from './person-getter';

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
  @Input() parent: PersonFamilyComponent;
  @Input() attribute: ApiAttribute;
  spouse: ApiPerson;

  constructor(public newPersonLinkService: NewPersonLinkService,
    personService: PersonService) {
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

  unlink(): void {
    const ub: UrlBuilder = new UrlBuilder(this.dataset, 'families', 'spouses');
    this.newPersonLinkService.delete(ub, this.parent.familyString(), this.spouse)
      .subscribe((data: ApiPerson) => { this.refreshPerson(); });
  }

  refreshPerson() {
    this.parent.refreshPerson();
  }
}
