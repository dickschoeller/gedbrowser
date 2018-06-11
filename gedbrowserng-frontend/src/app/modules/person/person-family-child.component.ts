import {Component, OnInit, Input, OnChanges} from '@angular/core';

import {ApiAttribute, ApiPerson} from '../../models';
import {PersonService, NewPersonLinkService} from '../../services';
import {PersonFamilyComponent} from './person-family.component';
import {PersonGetter} from './person-getter';

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
export class PersonFamilyChildComponent extends PersonGetter implements OnInit, OnChanges {
  @Input() dataset: string;
  @Input() parent: PersonFamilyComponent;
  @Input() child: ApiAttribute;
  @Input() index: number;
  person: ApiPerson;

  constructor(newPersonLinkService: NewPersonLinkService,
    personService: PersonService) {
    super(newPersonLinkService, personService);
    this.famMemberType = 'children';
  }

  ngOnInit() {
    this.init(this.dataset, this.child.string);
  }

  ngOnChanges() {
    this.init(this.dataset, this.child.string);
  }

  first(): boolean {
    return this.index === 0;
  }

  last(): boolean {
    return this.index + 1 >= this.parent.family.children.length;
  }

  familyString(): string {
    return this.parent.familyString();
  }

  refreshPerson(): void {
    this.parent.refreshPerson();
  }
}
