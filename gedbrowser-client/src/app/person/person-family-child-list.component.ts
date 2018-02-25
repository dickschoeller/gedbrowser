import {Component, Input} from '@angular/core';
import {MatDialogRef, MatDialog} from '@angular/material';

import {NewPersonDialogData, NewPersonDialogComponent, NewPersonHelper} from '../new-person-dialog';
import {ApiAttribute, ApiFamily, ApiPerson} from '../shared/models';
import {PersonService, FamilyService} from '../shared/services';
import { PersonFamilyComponent } from './person-family.component';

/**
 * Implements a child list within a family on a person page.
 *
 * Inputs:
 *  children: the attributes referring to the children
 */
@Component({
  selector: 'app-person-family-child-list',
  templateUrl: './person-family-child-list.component.html',
  styleUrls: ['./person-family-child-list.component.css']
})
export class PersonFamilyChildListComponent {
  @Input() children: Array<ApiAttribute>;
  @Input() family: ApiFamily;
  @Input() parentComponent: PersonFamilyComponent;

  constructor(public dialog: MatDialog,
    private personService: PersonService,
    private familyService: FamilyService) { }

  createChild(): void {
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
      this.saveNewPerson(dialogData);
    });
  }

  private saveNewPerson(dialogData: NewPersonDialogData): void {
    const nph = new NewPersonHelper();
    const newPerson: ApiPerson = nph.buildPerson(dialogData);
    newPerson.attributes.push({
      type: 'famc', string: this.family.string, tail: '', attributes: undefined
    });
    this.personService.post('schoeller', newPerson).subscribe(
      (data: ApiPerson) => {
        const person: ApiPerson = data;
        this.addChildToParent(person);
      }
    );
  }

  private addChildToParent(person: ApiPerson): void {
    this.parentComponent.childrenAttributes.push({
      type: 'child', string: person.string, tail: '', attributes: undefined
    });
    this.parentComponent.save();
  }
}
