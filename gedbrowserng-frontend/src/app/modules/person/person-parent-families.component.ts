import {Component, Input} from '@angular/core';
import {MenuItem} from 'primeng/api';

import {NewPersonDialogComponent, LinkPersonDialogComponent} from '../../components';
import {ApiAttribute, ApiPerson, ApiFamily, NewPersonDialogData, LinkPersonDialogData} from '../../models';
import {UrlBuilder, LinkPersonHelper} from '../../utils';
import {PersonService, NewPersonLinkService} from '../../services';

import {InitablePersonCreator} from '../../bases';
import {PersonComponent} from './person.component';

@Component({
  selector: 'app-person-parent-families',
  templateUrl: './person-parent-families.component.html',
  styleUrls: ['./person-parent-families.component.css']
})
export class PersonParentFamiliesComponent extends InitablePersonCreator {
  @Input() dataset: string;
  @Input() parent: PersonComponent;
  @Input() person: ApiPerson;
  displayPersonDialog = false;
  displayLinkParentDialog = false;
  surname: string;
  lph: LinkPersonHelper = new LinkPersonHelper();
  items: MenuItem[] = [
    {
      label: 'Create parent', icon: 'fa-user', command: (event: Event) => { this.createParentFamily(); }
    },
    {
      label: 'Link parent', icon: 'fa-link', command: (event: Event) => { this.openLinkParentDialog(); }
    },
  ];

  constructor(private personService: PersonService,
    newPersonLinkService: NewPersonLinkService) {
    super(newPersonLinkService);
  }

  init(): void {
    this.surname = this.person.surname;
  }

  createParentFamily() {
    this.displayPersonDialog = true;
  }

  onDialogOpen(dialog: NewPersonDialogComponent) {
    dialog._data = this.nph.initNew('M', this.person.surname);
  }

  personUB(): UrlBuilder {
    return new UrlBuilder(this.dataset, 'persons', 'parents');
  }

  closePersonDialog() {
    this.displayPersonDialog = false;
  }

  personAnchor () {
    return this.person.string;
  }

  refreshPerson() {
    this.personService.getOne(this.dataset, this.person.string).subscribe(
      (data: ApiPerson) => this.parent.person = data);
  }



  openLinkParentDialog() {
    this.displayLinkParentDialog = true;
  }

  onLinkParentDialogClose() {
    this.displayLinkParentDialog = false;
  }

  onLinkParentDialogOpen(dialogComponent: LinkPersonDialogComponent) {
    this.lph.onLinkChildDialogOpen(dialogComponent, this);
  }

  spouseLinked(person: ApiPerson): boolean {
//    for (const spouse of this.family.spouses) {
//      if (spouse.string === person.string) {
//        return true;
//      }
//    }
    return false;
  }

  childLinked(person: ApiPerson): boolean {
//    for (const child of this.children) {
//      if (child.string === person.string) {
//        return true;
//      }
//    }
    return false;
  }

  linkParent(data: LinkPersonDialogData) {
    this.newPersonLinkService.put(this.personUB(), this.personAnchor(), data.selectOne.person)
      .subscribe((person: ApiPerson) => { this.refreshPerson(); });
  }


}
