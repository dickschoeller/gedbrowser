import {Component, Input} from '@angular/core';
import {MenuItem} from 'primeng/api';

import {NewPersonDialogData, NewPersonDialog2Component} from '../../components';
import {ApiAttribute, ApiFamily, ApiPerson} from '../../models';
import {UrlBuilder} from '../../utils';
import {NewPersonLinkService, PersonService} from '../../services';

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
  displayS = false;
  displayC = false;
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
    this.displayC = true;
    this._ub = new UrlBuilder(this.dataset, 'persons', 'children');
  }

  createFamilyWithSpouse(): void {
    this.displayS = true;
    this._ub = new UrlBuilder(this.dataset, 'persons', 'spouses');
  }

  ub(): UrlBuilder {
    return this._ub;
  }

  onDialogOpenS(data: NewPersonDialog2Component) {
    data._data = this.nph.initNew(this.partnerSex, this.surnameS);
  }

  onDialogOpenC(data: NewPersonDialog2Component) {
    data._data = this.nph.initNew('M', this.surnameC);
  }

  closeDialog() {
    this.displayS = false;
    this.displayC = false;
  }

  anchor(): string {
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
