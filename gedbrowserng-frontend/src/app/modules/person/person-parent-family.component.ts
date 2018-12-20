import { Component, OnInit, Input, OnChanges } from '@angular/core';

import { InitablePersonCreator } from '../../bases';
import { ApiAttribute, ApiFamily, ApiPerson, LinkPersonDialogData } from '../../models';
import { FamilyService, PersonService } from '../../services';
import { RefreshPerson, Saveable, HasPerson, HasFamily } from '../../interfaces';
import { UrlBuilder } from '../../utils';

@Component({
  selector: 'app-person-parent-family',
  templateUrl: './person-parent-family.component.html',
  styleUrls: ['./person-parent-family.component.css']
})
export class PersonParentFamilyComponent extends InitablePersonCreator
  implements OnInit, OnChanges, HasFamily, RefreshPerson {
  @Input() dataset: string;
  @Input() parent: RefreshPerson & Saveable & HasPerson;
  @Input() attribute: ApiAttribute;

  family: ApiFamily;
  initialized = false;
  sex = 'M';
  get surname(): string {
    return this.parent.person.surname;
  }

  constructor(public personService: PersonService, private service: FamilyService) {
    super(personService);
  }

  init(): void {
    this.service.getOne(this.dataset, this.attribute.string)
      .subscribe((family: ApiFamily) => {
        this.family = family;
        this.initialized = true;
      });
  }

  familyString(): string {
    return this.family.string;
  }

  refreshPerson() {
    this.parent.refreshPerson();
  }

  personUB(): UrlBuilder {
    return new UrlBuilder(this.dataset, 'families', 'spouses');
  }

  personAnchor(): string {
    return this.family.string;
  }

  preferredSurname(): string {
    return this.surname;
  }

  childLinked(): boolean {
    return false;
  }

  spouseLinked(): boolean {
    return false;
  }
}
