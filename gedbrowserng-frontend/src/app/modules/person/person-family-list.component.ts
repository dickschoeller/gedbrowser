import {Component, Input} from '@angular/core';
import {MenuItem} from 'primeng/api';

import {NewPersonDialogComponent} from '../../components';
import {ApiAttribute, ApiFamily, ApiPerson, NewPersonDialogData} from '../../models';
import {UrlBuilder} from '../../utils';
import {NewPersonLinkService, PersonService} from '../../services';

import {InitablePersonCreator} from '../../bases';
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
export class PersonFamilyListComponent extends InitablePersonCreator {
  @Input() dataset: string;
  @Input() parent: PersonComponent;
  @Input() person: ApiPerson;
  items: MenuItem[] = [
    {
      label: 'Add family with partner', icon: 'fa-user-plus', command: (event: Event) => { this.createFamilyWithSpouse(); }
    },
    {
      label: 'Add family with child', icon: 'fa-user-plus', command: (event: Event) => { this.createFamilyWithChild(); }
    },
  ];
  displayPersonDialogS = false;
  displayPersonDialogC = false;
  surnameS: string;
  surnameC: string;
  _ub: UrlBuilder;
  partnerSex: string;

  constructor(newPersonLinkService: NewPersonLinkService,
    private personService: PersonService) {
    super(newPersonLinkService);
  }

  init() {
    this.partnerSex = this.nph.guessPartnerSex(this.person);
    if (this.partnerSex === 'M') {
      this.surnameC = '?';
    } else {
      this.surnameC = this.person.surname;
    }
    this.surnameS = '?';
  }

  createFamilyWithChild(): void {
    this.displayPersonDialogC = true;
    this._ub = new UrlBuilder(this.dataset, 'persons', 'children');
  }

  createFamilyWithSpouse(): void {
    this.displayPersonDialogS = true;
    this._ub = new UrlBuilder(this.dataset, 'persons', 'spouses');
  }

  onDialogOpenS(data: NewPersonDialogComponent) {
    data._data = this.nph.initNew(this.partnerSex, this.surnameS);
  }

  onDialogOpenC(data: NewPersonDialogComponent) {
    data._data = this.nph.initNew('M', this.surnameC);
  }

  personUB(): UrlBuilder {
    return this._ub;
  }

  closePersonDialog() {
    this.displayPersonDialogS = false;
    this.displayPersonDialogC = false;
  }

  personAnchor(): string {
    return this.person.string;
  }

  refreshPerson(): void {
    this.personService.getOne(this.dataset, this.person.string).subscribe(
      (person: ApiPerson) => this.updatePerson(person));
  }

  private updatePerson(person: ApiPerson) {
    this.person = person;
    this.parent.person = person;
  }
}
