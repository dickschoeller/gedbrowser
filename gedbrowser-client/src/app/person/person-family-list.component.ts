import {MatDialogRef, MatDialog} from '@angular/material';

import {NewPersonDialogData, NewPersonDialogComponent, NewPersonHelper} from '../new-person-dialog';
import {Component, Input} from '@angular/core';
import {ApiAttribute, ApiPerson} from '../shared';
import {ApiFamily} from '../shared/models';
import {SpouseService, PersonService} from '../shared/services';
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
export class PersonFamilyListComponent {
  @Input() parent: PersonComponent;
  @Input() person: ApiPerson;
  nph = new NewPersonHelper();

  constructor(public dialog: MatDialog,
    private spouseService: SpouseService,
    private personService: PersonService) { }

  createFamilyWithChild(): void {
    const dataIn = this.nph.initialData('M', 'Anonymous');
    const dialogRef: MatDialogRef<NewPersonDialogComponent> =
      this.dialog.open(NewPersonDialogComponent, this.nph.config(dataIn));
    dialogRef.afterClosed().subscribe(result => this.saveNewChild(result));
  }

  private saveNewChild(dialogData: NewPersonDialogData): void {
    if (this.nph.empty(dialogData)) {
      return;
    }
    const newPerson: ApiPerson = this.nph.buildPerson(dialogData);
//    this.childService.postChildToPerson('schoeller', this.person.string, newPerson).subscribe(
//      (data: ApiPerson) => this.refreshPerson());
  }

  createFamilyWithSpouse(): void {
    const dataIn: NewPersonDialogData = this.nph.initialData('F', 'Anonyma');
    const dialogRef: MatDialogRef<NewPersonDialogComponent> =
      this.dialog.open(NewPersonDialogComponent, this.nph.config(dataIn));
    dialogRef.afterClosed().subscribe(result => this.saveNewSpouse(result));
  }

  private saveNewSpouse(dialogData: NewPersonDialogData): void {
    if (this.nph.empty(dialogData)) {
      return;
    }
    const newPerson: ApiPerson = this.nph.buildPerson(dialogData);
    this.spouseService.postSpouseToPerson('schoeller', this.person.string, newPerson).subscribe(
      (data: ApiPerson) => this.refreshPerson());
  }

  private refreshPerson() {
    this.personService.getOne('schoeller', this.person.string).subscribe(
      (person: ApiPerson) => this.updatePerson(person));
  }

  private updatePerson(person: ApiPerson) {
    this.person = person;
    this.parent.person = person;
  }
}
