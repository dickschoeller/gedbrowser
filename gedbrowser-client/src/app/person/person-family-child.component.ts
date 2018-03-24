import {Component, OnInit, Input} from '@angular/core';
import {ApiAttribute, ApiPerson, PersonService, LifespanUtil} from '../shared';
import { PersonFamilyComponent } from './person-family.component';

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
export class PersonFamilyChildComponent implements OnInit {
  @Input() child: ApiAttribute;
  @Input() index: number;
  @Input() parentComponent: PersonFamilyComponent;
  person: ApiPerson;

  constructor(
    private personService: PersonService,
  ) { }

  ngOnInit() {
    this.personService.getOne('schoeller', this.child.string)
    .subscribe((person: ApiPerson) => {
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
    return this.index > this.parentComponent.childrenAttributes.length;
  }

  moveUp(): void {
    this.parentComponent.childrenAttributes.splice(
      this.index - 1, 0,
      this.parentComponent.childrenAttributes.splice(this.index, 1)[0]);
    this.parentComponent.save();
  }

  moveDown(): void {
    this.parentComponent.childrenAttributes.splice(
      this.index + 1, 0,
      this.parentComponent.childrenAttributes.splice(this.index, 1)[0]);
    this.parentComponent.save();
  }

  delete(): void {
    this.parentComponent.childrenAttributes.splice(this.index, 1);
    this.parentComponent.save();
  }
}
