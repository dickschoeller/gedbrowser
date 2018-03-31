import {Component, Input} from '@angular/core';
import {MatDialogRef, MatDialog} from '@angular/material';

import {NewPersonDialogData, NewPersonDialogComponent, NewPersonHelper} from '../new-person-dialog';
import {ApiAttribute, ApiFamily, ApiPerson} from '../shared/models';
import {ChildService, PersonService, FamilyService} from '../shared/services';
import { PersonCreator } from './person-creator';
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
export class PersonFamilyChildListComponent extends PersonCreator {
  @Input() children: Array<ApiAttribute>;
  @Input() family: ApiFamily;
  @Input() parentComponent: PersonFamilyComponent;
  nph = new NewPersonHelper();

  constructor(public dialog: MatDialog,
    private childService: ChildService,
    private personService: PersonService,
    private familyService: FamilyService) {
    super(dialog);
  }

  createChild(): void {
    this.newPersonDialog('M', 'Anonymous/' + this.parentComponent.person.surname + '/', this.saveNewPerson);
  }

  private saveNewPerson(dialogData: NewPersonDialogData, that: PersonFamilyChildListComponent): void {
    if (that.nph.empty(dialogData)) {
      return;
    }
    const newPerson: ApiPerson = that.nph.buildPerson(dialogData);
    that.childService.postChildToFamily('schoeller', that.family.string, newPerson).subscribe(
      (data: ApiPerson) => that.parentComponent.ngOnInit());
  }
}
