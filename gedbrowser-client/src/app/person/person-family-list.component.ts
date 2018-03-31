import {MatDialogRef, MatDialog} from '@angular/material';

import {NewPersonDialogData, NewPersonDialogComponent, NewPersonHelper} from '../new-person-dialog';
import {Component, Input} from '@angular/core';
import {ApiAttribute, ApiPerson} from '../shared';
import {ApiFamily} from '../shared/models';
import {SpouseService, PersonService} from '../shared/services';
import { PersonCreator } from './person-creator';
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
    private spouseService: SpouseService,
    private personService: PersonService) {
    super(dialog);
  }

  createFamilyWithChild(): void {
    this.newPersonDialog('M', 'Anonymous', this.saveNewChild);
  }

  saveNewChild(dialogData: NewPersonDialogData, that: PersonFamilyListComponent): void {
    if (that.nph.empty(dialogData)) {
      return;
    }
    const newPerson: ApiPerson = that.nph.buildPerson(dialogData);
  }

  createFamilyWithSpouse(): void {
    this.newPersonDialog('F', 'Anonyma', this.saveNewSpouse);
  }

  saveNewSpouse(dialogData: NewPersonDialogData, that: PersonFamilyListComponent): void {
    if (that.nph.empty(dialogData)) {
      return;
    }
    const newPerson: ApiPerson = that.nph.buildPerson(dialogData);
    that.spouseService.postSpouseToPerson('schoeller', that.person.string, newPerson).subscribe(
      (data: ApiPerson) => that.refreshPerson());
  }

  private refreshPerson() {
    this.personService.getOne('schoeller', this.person.string).subscribe(
      (person: ApiPerson) => this.updatePerson(person));
  }

  private updatePerson(person: ApiPerson) {
    this.person = person;
    this.parent.person = person;
  }

  // Implemented for the interface. Not needed here.
  public saveNewParent(dialogData: NewPersonDialogData) { }
}
