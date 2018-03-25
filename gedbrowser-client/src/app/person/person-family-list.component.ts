import {MatDialogRef, MatDialog} from '@angular/material';

import {NewPersonDialogData, NewPersonDialogComponent, NewPersonHelper} from '../new-person-dialog';
import {Component, Input} from '@angular/core';
import {ApiAttribute, ApiPerson} from '../shared';
import {ApiFamily} from '../shared/models';
import {SpouseService, PersonService, ChildService} from '../shared/services';
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

  constructor(public dialog: MatDialog,
    private spouseService: SpouseService,
    private personService: PersonService,
    private childService: ChildService) { }

  createFamilyWithChild(): void {
    const dataIn: NewPersonDialogData = {
      sex: 'M', name: 'Anonymous',
      birthDate: '', birthPlace: '', deathDate: '', deathPlace: ''
    };
    const dialogRef: MatDialogRef<NewPersonDialogComponent> =
      this.dialog.open(NewPersonDialogComponent, {
        width: '500px',
        height: '600px',
        data: dataIn,
      });
    dialogRef.afterClosed().subscribe(result => {
      if (result === null || result === undefined) {
        return;
      }
      const dialogData: NewPersonDialogData = result;
      this.saveNewChild(dialogData);
    });
  }

  private saveNewChild(dialogData: NewPersonDialogData): void {
    const nph = new NewPersonHelper();
    const newPerson: ApiPerson = nph.buildPerson(dialogData);
    this.childService.postToPerson('schoeller', this.person.string, newPerson).subscribe(
      (data: ApiPerson) => {
        this.personService.getOne('schoeller', this.person.string).subscribe(
          (person: ApiPerson) => {
            this.person = person;
            this.parent.person = person;
            this.parent.initLists();
          });
      }
    );
  }

  createFamilyWithSpouse(): void {
    const dataIn: NewPersonDialogData = {
      sex: 'F', name: 'Anonyma',
      birthDate: '', birthPlace: '', deathDate: '', deathPlace: ''
    };
    const dialogRef: MatDialogRef<NewPersonDialogComponent> =
      this.dialog.open(NewPersonDialogComponent, {
        width: '500px',
        height: '600px',
        data: dataIn,
      });
    dialogRef.afterClosed().subscribe(result => {
      if (result === null || result === undefined) {
        return;
      }
      const dialogData: NewPersonDialogData = result;
      this.saveNewSpouse(dialogData);
    });
  }

  private saveNewSpouse(dialogData: NewPersonDialogData): void {
    const nph = new NewPersonHelper();
    const newPerson: ApiPerson = nph.buildPerson(dialogData);
    this.spouseService.postToPerson('schoeller', this.person.string, newPerson).subscribe(
      (data: ApiPerson) => {
        this.personService.getOne('schoeller', this.person.string).subscribe(
          (person: ApiPerson) => {
            this.person = person;
            this.parent.person = person;
            this.parent.initLists();
          });
      }
    );
  }
}
