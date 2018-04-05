import {Component, Input} from '@angular/core';
import {MatDialogRef, MatDialog} from '@angular/material';

import {ApiAttribute, ApiFamily, ApiPerson} from '../../models';
import {NewPersonLinkService, PersonService, UrlBuilder} from '../../services';

import {NewPersonDialogData, NewPersonDialogComponent, NewPersonHelper} from '../../components/new-person-dialog';

import {PersonCreator} from './person-creator';
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
export class PersonFamilyListComponent extends PersonCreator {
  @Input() parent: PersonComponent;
  @Input() person: ApiPerson;

  constructor(public dialog: MatDialog,
    private newPersonLinkService: NewPersonLinkService,
    private personService: PersonService) {
    super(dialog);
  }

  createFamilyWithChild(): void {
    this.newPersonDialog2('M', 'Anonymous', this.newPersonLinkService,
      new UrlBuilder('schoeller', 'persons', 'children'));
  }

  createFamilyWithSpouse(): void {
    this.newPersonDialog2('F', 'Anonyma', this.newPersonLinkService,
      new UrlBuilder('schoeller', 'persons', 'spouses'));
  }

  anchor(): string {
    return this.person.string;
  }

  refreshPerson(): void {
    this.personService.getOne('schoeller', this.person.string).subscribe(
      (person: ApiPerson) => this.updatePerson(person));
  }

  private updatePerson(person: ApiPerson) {
    this.person = person;
    this.parent.person = person;
  }
}
