import {Component, Input} from '@angular/core';
import {MatDialogRef, MatDialog} from '@angular/material';

import {ApiAttribute, ApiPerson, ApiFamily} from '../shared/models';
import {PersonService, NewPersonLinkService} from '../shared/services';
import {UrlBuilder} from '../shared/services/urlbuilder';
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
    private newPersonLinkService: NewPersonLinkService) {
    super(dialog);
  }

  createParentFamily() {
    this.newPersonDialog2('M', 'Anonymous/' + this.person.surname + '/',
      this.newPersonLinkService, new UrlBuilder('schoeller', 'persons', 'parents'));
  }

  anchor () {
    return this.person.string;
  }

  refreshPerson() {
    this.personService.getOne('schoeller', this.person.string).subscribe(
      (data: ApiPerson) => this.parent.person = data);
  }
}
