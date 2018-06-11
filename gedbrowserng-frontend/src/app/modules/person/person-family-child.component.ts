import {Component, OnInit, Input, OnChanges} from '@angular/core';

import {ApiAttribute, ApiPerson} from '../../models';
import {PersonService, NewPersonLinkService} from '../../services';
import {LifespanUtil, UrlBuilder} from '../../utils';
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

  constructor(public newPersonLinkService: NewPersonLinkService,
    personService: PersonService) {
    super(personService);
  }

  ngOnInit() {
    this.init(this.dataset, this.child.string);
  }

  ngOnChanges() {
    this.init(this.dataset, this.child.string);
  }

  lifespanYearString(): string {
    return new LifespanUtil(this.person.lifespan).lifespanYearString();
  }

  first(): boolean {
    return this.index === 0;
  }

  last(): boolean {
    return this.index + 1 >= this.parent.family.children.length;
  }

  delete(): void {
    const ub: UrlBuilder = new UrlBuilder(this.dataset, 'families', 'children');
    this.newPersonLinkService.delete(ub, this.parent.familyString(), this.person)
      .subscribe((data: ApiPerson) => { this.parent.refreshPerson(); });
  }
}
