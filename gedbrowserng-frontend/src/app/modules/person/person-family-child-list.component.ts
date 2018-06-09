import {Component, Input} from '@angular/core';
import {MenuItem} from 'primeng/api';

import {NewPersonDialogComponent, LinkPersonDialogComponent} from '../../components';
import {ApiAttribute, ApiFamily, ApiPerson, NewPersonDialogData, LinkPersonDialogData} from '../../models';
import {NewPersonLinkService, PersonService} from '../../services';
import {NewPersonHelper, UrlBuilder, LifespanUtil, LinkPersonHelper} from '../../utils';
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
  @Input() parent: PersonFamilyComponent;
  @Input() family: ApiFamily;
  @Input() children: Array<ApiAttribute>;
  displayPersonDialog = false;
  displayLinkChildDialog = false;
  surname: string;
  items: MenuItem[] = [
    {
      label: 'Create child', icon: 'fa-user', command: (event: Event) => { this.createChild2(); }
    },
    {
      label: 'Link child', icon: 'fa-link', command: (event: Event) => { this.openLinkChildDialog(); }
    },
  ];
  lph: LinkPersonHelper = new LinkPersonHelper();

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

  onLinkChildDialogOpen(dialogComponent: LinkPersonDialogComponent) {
    this.lph.onLinkChildDialogOpen(dialogComponent, this);
  }

  spouseLinked(person: ApiPerson): boolean {
    for (const spouse of this.family.spouses) {
      if (spouse.string === person.string) {
        return true;
      }
    }
    return false;
  }

  childLinked(person: ApiPerson): boolean {
    for (const child of this.children) {
      if (child.string === person.string) {
        return true;
      }
    }
    return false;
  }

  linkChild(data: LinkPersonDialogData) {
    for (const item of data.selected) {
      this.newPersonLinkService.put(this.personUB(), this.personAnchor(), item.person)
        .subscribe((person: ApiPerson) => { this.refreshPerson(); });
    }
  }
}
