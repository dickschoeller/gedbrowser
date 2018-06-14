import { InitablePersonCreator } from '../../bases';
import { LinkPersonDialogComponent, NewPersonDialogComponent } from '../../components';
import { Component, OnInit, Input, OnChanges } from '@angular/core';
import { MenuItem } from 'primeng/api';

import { ApiAttribute, ApiFamily, ApiPerson, LinkPersonDialogData } from '../../models';
import { FamilyService, NewPersonLinkService, PersonService } from '../../services';
import { RefreshPerson, Saveable, HasPerson, HasFamily } from '../../interfaces';
import { UrlBuilder, LinkPersonHelper } from '../../utils';

@Component({
  selector: 'app-person-parent-family',
  templateUrl: './person-parent-family.component.html',
  styleUrls: ['./person-parent-family.component.css']
})
export class PersonParentFamilyComponent extends InitablePersonCreator
  implements OnInit, OnChanges, HasFamily, RefreshPerson {
  @Input() dataset: string;
  @Input() parent: RefreshPerson & Saveable & HasPerson;
  @Input() attribute: ApiAttribute;
  family: ApiFamily;
  initialized = false;
  lph: LinkPersonHelper = new LinkPersonHelper();
  items: MenuItem[] = [
    {
      label: 'Create parent', icon: 'fa-user', command: (event: Event) => {  this.displayPersonDialog = true; }
    },
    {
      label: 'Link parent', icon: 'fa-link', command: (event: Event) => { this.displayLinkParentDialog = true; }
    },
  ];
  displayPersonDialog = false;
  displayLinkParentDialog = false;

  constructor(
    private personService: PersonService,
    private service: FamilyService,
    newPersonLinkService: NewPersonLinkService
  ) {
    super(newPersonLinkService);
  }

  init(): void {
    this.service.getOne(this.dataset, this.attribute.string)
      .subscribe((family: ApiFamily) => {
        this.family = family;
        this.initialized = true;
      });
  }

  familyString(): string {
    return this.family.string;
  }

  refreshPerson() {
    this.parent.refreshPerson();
  }

  personUB(): UrlBuilder {
    return new UrlBuilder(this.dataset, 'families', 'spouses');
  }

  personAnchor(): string {
    return this.family.string;
  }

  closePersonDialog(): void {
    this.displayPersonDialog = false;
  }

  linkParent(data: LinkPersonDialogData) {
    this.newPersonLinkService.put(this.personUB(), this.personAnchor(), data.selectOne.person)
      .subscribe((person: ApiPerson) => { this.refreshPerson(); });
  }

  onLinkParentDialogClose() {
    this.displayLinkParentDialog = false;
  }

  onLinkParentDialogOpen(dialogComponent: LinkPersonDialogComponent) {
    this.lph.onLinkChildDialogOpen(dialogComponent, this);
  }

  onDialogOpen(dialog: NewPersonDialogComponent) {
    dialog._data = this.nph.initNew('M', this.parent.person.surname);
  }

  preferredSurname(): string {
    return 'Xyzzy';
  }

  childLinked(): boolean {
    return false;
  }

  spouseLinked(): boolean {
    return false;
  }
}
