import {Component, Input} from '@angular/core';
import {MatDialogRef, MatDialog} from '@angular/material';

import {NewPersonDialogData, NewPersonDialogComponent, NewPersonHelper} from '../new-person-dialog';
import {ApiAttribute, ApiPerson, ApiFamily} from '../shared/models';
import {PersonService, ParentService} from '../shared/services';
import {PersonCreator} from './person-creator';
import {PersonComponent} from './person.component';

@Component({
  selector: 'app-person-parent-families',
  templateUrl: './person-parent-families.component.html',
  styleUrls: ['./person-parent-families.component.css']
})
export class PersonParentFamiliesComponent extends PersonCreator {
  @Input() parent: PersonComponent;
  @Input() person: ApiPerson;

  constructor(public dialog: MatDialog,
    private personService: PersonService,
    private parentService: ParentService) {
    super(dialog);
  }

  createParentFamily() {
    this.newPersonDialog1('M', 'Anonymous/' + this.person.surname + '/', this.parentService);
  }

  anchor () {
    return this.person.string;
  }

  refreshPerson() {
    this.personService.getOne('schoeller', this.person.string).subscribe(
      (data: ApiPerson) => this.parent.person = data);
  }
}
