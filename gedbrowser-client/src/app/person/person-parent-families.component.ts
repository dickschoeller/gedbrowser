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
  nph = new NewPersonHelper();

  constructor(public dialog: MatDialog,
    private personService: PersonService,
    private familyService: FamilyService,
    private parentService: ParentService) {}

  createParentFamily() {
    const dataIn: NewPersonDialogData =
      this.nph.initialData('M', 'Anonymous/' + this.person.surname + '/');
    const dialogRef: MatDialogRef<NewPersonDialogComponent> =
      this.dialog.open(NewPersonDialogComponent, this.nph.config(dataIn));
    dialogRef.afterClosed().subscribe(result => this.saveNewParent(result));
  }

  private saveNewParent(dialogData: NewPersonDialogData) {
    if (this.nph.empty(dialogData)) {
      return;
    }
    const newPerson: ApiPerson = this.nph.buildPerson(dialogData);
    this.parentService.postToPerson('schoeller', this.person.string, newPerson).subscribe(
      (data: ApiPerson) => this.refreshPerson());
  }

  private refreshPerson() {
    this.personService.getOne('schoeller', this.person.string).subscribe(
      (data: ApiPerson) => this.parent.person = data);
  }
}
