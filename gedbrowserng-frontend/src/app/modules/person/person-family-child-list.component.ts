import {Component, Input} from '@angular/core';

import {NewPersonDialogComponent, LinkPersonDialogComponent} from '../../components';
import {ApiAttribute, ApiFamily, ApiPerson, NewPersonDialogData, LinkPersonDialogData} from '../../models';
import {NewPersonLinkService, PersonService} from '../../services';
import {NewPersonHelper, UrlBuilder, LifespanUtil} from '../../utils';

import {InitablePersonCreator} from '../../bases';
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
export class PersonFamilyChildListComponent extends InitablePersonCreator {
  @Input() dataset: string;
  @Input() children: Array<ApiAttribute>;
  @Input() family: ApiFamily;
  @Input() parent: PersonFamilyComponent;
  displayPersonDialog = false;
  displayLinkChildDialog = false;
  surname: string;

  constructor(newPersonLinkService: NewPersonLinkService,
    private personService: PersonService) {
    super(newPersonLinkService);
  }

  init(): void {
    const h = this.husbandId();
    if (h !== '') {
      this.personService.getOne(this.dataset, h)
        .subscribe((person: ApiPerson) => {
          this.surname = person.surname;
        });
    } else {
      this.surname = this.parent.person.surname;
    }
  }

  private husbandId(): string {
    for (const spouse of this.family.spouses) {
      if (spouse.type === 'husband') {
        return spouse.string;
      }
    }
    return '';
  }

  createChild2(): void {
    this.displayPersonDialog = true;
  }

  onDialogOpen(data: NewPersonDialogComponent) {
    data._data = this.nph.initNew('M', this.surname);
  }

  closePersonDialog() {
    this.displayPersonDialog = false;
  }

  personUB(): UrlBuilder {
    return new UrlBuilder(this.dataset, 'families', 'children');
  }

  personAnchor(): string {
    return this.family.string;
  }

  refreshPerson(): void {
    this.personService.getOne(this.dataset, this.parent.person.string).subscribe(
      (person: any) => this.parent.refreshPerson());
  }

  openLinkChildDialog() {
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
    for (const spouse of this.family.spouses) {
      if (spouse.string === person.string) {
        return true;
      }
    }
    return false;
  }

  private childLinked(person: ApiPerson): boolean {
    for (const child of this.children) {
      if (child.string === person.string) {
        return true;
      }
    }
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
    for (const item of data.selected) {
      this.newPersonLinkService.put(this.personUB(), this.personAnchor(), item.person)
        .subscribe((person: ApiPerson) => {});
    }
    this.refreshPerson();
  }
}
