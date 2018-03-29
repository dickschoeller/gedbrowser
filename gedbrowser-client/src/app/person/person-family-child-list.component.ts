import {Component, Input} from '@angular/core';
import {MatDialogRef, MatDialog} from '@angular/material';

import {NewPersonDialogData, NewPersonDialogComponent, NewPersonHelper} from '../new-person-dialog';
import {ApiAttribute, ApiFamily, ApiPerson} from '../shared/models';
import {ChildService, PersonService, FamilyService} from '../shared/services';
import {PersonFamilyComponent} from './person-family.component';

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
  nph = new NewPersonHelper();

  constructor(public dialog: MatDialog,
    private childService: ChildService,
    private personService: PersonService,
    private familyService: FamilyService) { }

  createChild(): void {
    const dataIn: NewPersonDialogData = this.nph.initialData(
      'M', 'Anonymous/' + this.parentComponent.person.surname + '/');
    const dialogRef: MatDialogRef<NewPersonDialogComponent> =
      this.dialog.open(NewPersonDialogComponent, this.nph.config(dataIn));

    dialogRef.afterClosed().subscribe(result => {
      this.saveNewPerson(result);
    });
  }

  private saveNewPerson(dialogData: NewPersonDialogData): void {
    if (this.nph.empty(dialogData)) {
      return;
    }
    const newPerson: ApiPerson = this.nph.buildPerson(dialogData);
    this.childService.postChildToFamily('schoeller', this.family.string, newPerson).subscribe(
      (data: ApiPerson) => this.parentComponent.ngOnInit());
  }
}
