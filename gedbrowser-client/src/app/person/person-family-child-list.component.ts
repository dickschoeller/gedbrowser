import {Component, Input} from '@angular/core';
import {MatDialogRef, MatDialog} from '@angular/material';

import {ApiAttribute, ApiFamily} from '../shared/models';
import {NewPersonLinkService, PersonService} from '../shared/services';
import { UrlBuilder } from '../shared/services/urlbuilder';
import {PersonCreator} from './person-creator';
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

  constructor(public dialog: MatDialog,
    private newPersonLinkService: NewPersonLinkService,
    private personService: PersonService) {
    super(dialog);
  }

  createChild(): void {
    this.newPersonDialog2('M', 'Anonymous/' + this.parentComponent.person.surname + '/',
      this.newPersonLinkService,
      new UrlBuilder('schoeller', 'families', 'children'));
  }

  anchor(): string {
    return this.family.string;
  }

  refreshPerson(): void {
    this.personService.getOne('schoeller', this.parentComponent.person.string).subscribe(
      (person: any) => this.parentComponent.refreshPerson());
  }
}
