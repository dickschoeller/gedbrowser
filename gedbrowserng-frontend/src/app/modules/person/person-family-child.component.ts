import {Component, OnInit, Input, OnChanges} from '@angular/core';

import {ApiAttribute, ApiPerson} from '../../models';
import {PersonService} from '../../services';
import {LifespanUtil} from '../../utils';

import {PersonFamilyComponent} from './person-family.component';
import { PersonGetter } from './person-getter';

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
  @Input() child: ApiAttribute;
  @Input() index: number;
  @Input() parent: PersonFamilyComponent;
  person: ApiPerson;

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
    this.get(this.dataset, this.child.string, (person: ApiPerson) => {
      this.person = person;
    });
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
    this.parent.family.children.splice(this.index, 1);
    this.parent.save();
  }
}
