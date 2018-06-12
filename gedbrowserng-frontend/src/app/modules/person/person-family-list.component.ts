import { Component, Input } from '@angular/core';
import { MenuItem } from 'primeng/api';

import { NewPersonDialogComponent, LinkPersonDialogComponent } from '../../components';
import { ApiAttribute, ApiFamily, ApiPerson, NewPersonDialogData, LinkPersonDialogData, LinkPersonItem } from '../../models';
import { UrlBuilder } from '../../utils';
import { NewPersonLinkService, PersonService } from '../../services';
import { LifespanUtil, LinkPersonHelper } from '../../utils';

import { InitablePersonCreator } from '../../bases';
import { HasPerson } from '../../interfaces';

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
  @Input() parent: HasPerson;
  get person(): ApiPerson {
    return this.parent.person;
  }
  items: MenuItem[] = [
    {
      label: 'Add family, create partner', icon: 'fa-user-plus', command: (event: Event) => { this.createFamilyWithSpouse(); }
    },
    {
      label: 'Add family, link partner', icon: 'fa-link', command: (event: Event) => { this.openLinkSpouseDialog(); }
    },
    {
      label: 'Add family, create child', icon: 'fa-user-plus', command: (event: Event) => { this.createFamilyWithChild(); }
    },
    {
      label: 'Add family, link child', icon: 'fa-link', command: (event: Event) => { this.createFamilyLinkChild(); }
    },
  ];
  displayPersonDialogS = false;
  displayPersonDialogC = false;
  displayLinkChildDialog = false;
  displayLinkSpouseDialog = false;
  surnameS: string;
  surnameC: string;
  _ub: UrlBuilder;
  partnerSex: string;
  lph: LinkPersonHelper = new LinkPersonHelper();

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
    this.parent.person = person;
  }

  createFamilyLinkChild() {
    this._ub = new UrlBuilder(this.dataset, 'persons', 'children');
    this.displayLinkChildDialog = true;
  }

  onLinkChildDialogClose() {
    this.displayLinkChildDialog = false;
  }

  onLinkChildDialogOpen(dialogComponent: LinkPersonDialogComponent) {
    this.lph.onLinkChildDialogOpen(dialogComponent, this);
  }

  spouseLinked(person: ApiPerson): boolean {
    return this.person.string === person.string;
  }

  childLinked(person: ApiPerson): boolean {
    return false;
  }

  linkChildren(data: LinkPersonDialogData) {
    const selected: Array<LinkPersonItem> = data.selected.splice(0, 1);
    this.newPersonLinkService.put(this.personUB(), this.personAnchor(), selected[0].person)
      .subscribe((person: ApiPerson) => {
        this.linkChildrenToMainPerson(data);
      });
  }

  private linkChildrenToMainPerson(data: LinkPersonDialogData) {
    this.personService.getOne(this.dataset, this.person.string)
      .subscribe((mainPerson: ApiPerson) => {
        this.linkRemainingChildren(data, mainPerson);
      });
  }

  private linkRemainingChildren(data: LinkPersonDialogData, mainPerson: ApiPerson): void {
    this.updatePerson(mainPerson);
    const fams = mainPerson.fams[mainPerson.fams.length - 1];
    const ub = new UrlBuilder(this.dataset, 'families', 'children');
    for (const selected of data.selected) {
      this.newPersonLinkService.put(ub, fams.string, selected.person)
        .subscribe((p: ApiPerson) => { this.refreshPerson(); });
    }
  }





  openLinkSpouseDialog() {
    this._ub = new UrlBuilder(this.dataset, 'persons', 'spouses');
    this.displayLinkSpouseDialog = true;
  }

  onLinkSpouseDialogClose() {
    this.displayLinkSpouseDialog = false;
  }

  onLinkSpouseDialogOpen(dialogComponent: LinkPersonDialogComponent) {
    this.lph.onLinkChildDialogOpen(dialogComponent, this);
  }

  linkSpouse(data: LinkPersonDialogData) {
    this.newPersonLinkService.put(this.personUB(), this.personAnchor(), data.selectOne.person)
      .subscribe((person: ApiPerson) => { this.refreshPerson(); });
  }





}
