import { Component, OnInit, Input, OnChanges } from '@angular/core';

import { ApiAttribute, ApiPerson } from '../../models';
import { PersonService, NewPersonLinkService } from '../../services';
import { HasFamily } from '../../interfaces/has-family';
import { PersonGetter } from './person-getter';
import { RefreshPerson } from '../../interfaces';

/**
 * Implements a spouse block within a family on a person page.
 *
 * Inputs:
 *  attribute: the attribute referring to the spouse
 *
 * Fetches:
 *  person: the person identified by the attribute
 */
@Component({
  selector: 'app-person-family-spouse',
  templateUrl: './person-family-spouse.component.html',
  styleUrls: ['./person-family-spouse.component.css']
})
export class PersonFamilySpouseComponent extends PersonGetter
  implements OnInit, OnChanges {
  @Input() dataset: string;
  @Input() parent: RefreshPerson & HasFamily;
  @Input() attribute: ApiAttribute;

  constructor(newPersonLinkService: NewPersonLinkService,
    personService: PersonService) {
    super(newPersonLinkService, personService);
    this.famMemberType = 'spouses';
  }

  ngOnInit() {
    this.init(this.dataset, this.attribute.string);
  }

  ngOnChanges() {
    this.init(this.dataset, this.attribute.string);
  }

  familyString(): string {
    return this.parent.familyString();
  }

  refreshPerson(): void {
    this.parent.refreshPerson();
  }
}
