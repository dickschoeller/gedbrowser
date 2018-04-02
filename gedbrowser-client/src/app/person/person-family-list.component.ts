import {MatDialogRef, MatDialog} from '@angular/material';

import {NewPersonDialogData, NewPersonDialogComponent, NewPersonHelper} from '../new-person-dialog';
import {Component, Input} from '@angular/core';
import {ApiAttribute, ApiPerson} from '../shared';
import {ApiFamily} from '../shared/models';
import {ChildToPersonService, SpouseToPersonService, PersonService} from '../shared/services';
import {PersonCreator} from './person-creator';
import {PersonComponent} from './person.component';

/**
 * Implements a the list of families on a person page
 *
 * Inputs:
 *  person: the person this page is for
 */
@Component({
  selector: 'app-person-family-list',
  templateUrl: './person-family-list.component.html',
  styleUrls: ['./person-family-list.component.css']
})
export class PersonFamilyListComponent extends PersonCreator {
  @Input() parent: PersonComponent;
  @Input() person: ApiPerson;

  constructor(public dialog: MatDialog,
    private childToPersonService: ChildToPersonService,
    private spouseToPersonService: SpouseToPersonService,
    private personService: PersonService) {
    super(dialog);
  }

  createFamilyWithChild(): void {
    this.newPersonDialog1('M', 'Anonymous', this.childToPersonService);
  }

  createFamilyWithSpouse(): void {
    this.newPersonDialog1('F', 'Anonyma', this.spouseToPersonService);
  }

  anchor(): string {
    return this.person.string;
  }

  refreshPerson(): void {
    this.personService.getOne('schoeller', this.person.string).subscribe(
      (person: ApiPerson) => this.updatePerson(person));
  }

  private updatePerson(person: ApiPerson) {
    this.person = person;
    this.parent.person = person;
  }
}
