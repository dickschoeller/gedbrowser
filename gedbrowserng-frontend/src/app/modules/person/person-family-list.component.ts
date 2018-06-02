import {Component, Input} from '@angular/core';
import {MenuItem} from 'primeng/api';

import {NewPersonDialogComponent, LinkPersonDialogComponent} from '../../components';
import {ApiAttribute, ApiFamily, ApiPerson, NewPersonDialogData, LinkPersonDialogData, LinkPersonItem} from '../../models';
import {UrlBuilder} from '../../utils';
import {NewPersonLinkService, PersonService} from '../../services';
import {LifespanUtil} from '../../utils';

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
      label: 'Add family, create partner', icon: 'fa-user-plus', command: (event: Event) => { this.createFamilyWithSpouse(); }
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

  createFamilyLinkChild() {
    this._ub = new UrlBuilder(this.dataset, 'persons', 'children');
    this.displayLinkChildDialog = true;
  }

  onLinkChildDialogClose() {
    this.displayLinkChildDialog = false;
  }

  onLinkChildDialogOpen(data: LinkPersonDialogComponent) {
    this.personService.getAll(data.dataset).subscribe(
      (value: ApiPerson[]) => {
        data.persons = value;
        data.persons.sort(data.compare);
        data._data = new LinkPersonDialogData();
        for (const person of data.persons) {
          if (this.alreadyLinked(person)) {
            continue;
          }
          this.pushDataItem(data, person);
        }
      }
    );
  }

  private alreadyLinked(person: ApiPerson): boolean {
    if (this.spouseLinked(person)) {
      return true;
    }
    return this.childLinked(person);
  }

  private spouseLinked(person: ApiPerson): boolean {
    if (this.person.string === person.string) {
      return true;
    }
    return false;
  }

  private childLinked(person: ApiPerson): boolean {
//    for (const child of this.children) {
//      if (child.string === person.string) {
//        return true;
//      }
//    }
    return false;
  }

  private pushDataItem(data, person) {
    const lifespanUtil = new LifespanUtil(person.lifespan);
    data._data.items.push({
      id: person.string,
      label: person.indexName
        + lifespanUtil.lifespanYearString()
        + ' [' + person.string + ']',
      person: person
    });
  }

  linkChild(data: LinkPersonDialogData) {
    const selected: Array<LinkPersonItem> = data.selected.splice(0, 1);
    this.newPersonLinkService.put(this.personUB(), this.personAnchor(), selected[0].person)
      .subscribe((person: ApiPerson) => {
        this.personService.getOne(this.dataset, this.person.string)
          .subscribe((mainPerson: ApiPerson) => {
            this.updatePerson(mainPerson);
            const fams = mainPerson.fams[mainPerson.fams.length - 1];
            const ub = new UrlBuilder(this.dataset, 'families', 'children');
            for (const item1 of data.selected) {
              this.newPersonLinkService.put(ub, fams.string, item1.person)
                .subscribe((p: ApiPerson) => { this.refreshPerson(); });
            }
          });
      });
  }
}
