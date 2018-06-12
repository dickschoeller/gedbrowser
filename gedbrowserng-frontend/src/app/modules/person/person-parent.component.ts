import { Component, OnInit, Input, OnChanges } from '@angular/core';

import { ApiAttribute, ApiPerson } from '../../models';
import { PersonService, NewPersonLinkService } from '../../services';
import { HasFamily } from '../../interfaces/has-family';
import { PersonGetter } from './person-getter';
import { RefreshPerson } from '../../interfaces';

@Component({
  selector: 'app-person-parent',
  templateUrl: './person-parent.component.html',
  styleUrls: ['./person-parent.component.css']
})
export class PersonParentComponent extends PersonGetter implements OnInit, OnChanges {
  @Input() dataset: string;
  @Input() parent: HasFamily & RefreshPerson;
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

  label(): string {
    if (this.attribute.type === 'wife') {
      return 'Mother';
    }
    if (this.attribute.type === 'husband') {
      return 'Father';
    }
    return 'Parent';
  }

  familyString(): string {
    return this.parent.familyString();
  }

  refreshPerson() {
    this.parent.refreshPerson();
  }
}
