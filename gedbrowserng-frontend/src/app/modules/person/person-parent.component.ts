import {Component, OnInit, Input, OnChanges} from '@angular/core';

import {ApiAttribute, ApiPerson} from '../../models';
import {PersonService, NewPersonLinkService} from '../../services';
import {LifespanUtil, UrlBuilder} from '../../utils';
import {PersonParentFamilyComponent} from './person-parent-family.component';
import {PersonGetter} from './person-getter';

@Component({
  selector: 'app-person-parent',
  templateUrl: './person-parent.component.html',
  styleUrls: ['./person-parent.component.css']
})
export class PersonParentComponent extends PersonGetter implements OnInit, OnChanges {
  @Input() dataset: string;
  @Input() parent: PersonParentFamilyComponent;
  @Input() attribute: ApiAttribute;

  constructor(public newPersonLinkService: NewPersonLinkService,
    personService: PersonService) {
    super(personService);
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

  lifespanYearString(): string {
    return new LifespanUtil(this.person.lifespan).lifespanYearString();
  }

  unlink(): void {
    const ub: UrlBuilder = new UrlBuilder(this.dataset, 'families', 'spouses');
    this.newPersonLinkService.delete(ub, this.parent.familyString(), this.person)
      .subscribe((data: ApiPerson) => { this.refreshPerson(); });
  }

  refreshPerson() {
    this.parent.refreshPerson();
  }
}
