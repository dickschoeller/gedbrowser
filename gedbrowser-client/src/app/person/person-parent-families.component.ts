import {Component, Input} from '@angular/core';
import {MatDialogRef, MatDialog} from '@angular/material';

import {NewPersonDialogData, NewPersonDialogComponent, NewPersonHelper} from '../new-person-dialog';
import {ApiAttribute, ApiPerson, ApiFamily} from '../shared/models';
import {PersonService, FamilyService, ParentService} from '../shared/services';
import {PersonComponent} from './person.component';

@Component({
  selector: 'app-person-parent-families',
  templateUrl: './person-parent-families.component.html',
  styleUrls: ['./person-parent-families.component.css']
})
export class PersonParentFamiliesComponent {
  @Input() parent: PersonComponent;
  @Input() person: ApiPerson;

  constructor(public dialog: MatDialog,
    private personService: PersonService,
    private familyService: FamilyService,
    private parentService: ParentService) {}

  createParentFamily() {
    const dataIn: NewPersonDialogData = {
      sex: 'M', name: 'Anonymous/' + this.person.surname + '/',
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
      this.saveNewParent(dialogData);
    });
  }

  private saveNewParent(dialogData: NewPersonDialogData) {
    const nph = new NewPersonHelper();
    const newPerson: ApiPerson = nph.buildPerson(dialogData);
    this.parentService.postToPerson('schoeller', this.person.string, newPerson).subscribe(
      (data: ApiPerson) => {
        this.parent.person = data;
        this.parent.initLists();
      }
    );
  }
}
