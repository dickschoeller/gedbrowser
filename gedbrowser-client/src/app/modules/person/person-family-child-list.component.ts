import {Component, Input} from '@angular/core';

import {NewPersonDialog2Component, NewPersonDialogData, NewPersonHelper} from '../../components';
import {ApiAttribute, ApiFamily, ApiPerson} from '../../models';
import {NewPersonLinkService, PersonService, UrlBuilder} from '../../services';

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
  @Input() dataset: string;
  @Input() children: Array<ApiAttribute>;
  @Input() family: ApiFamily;
  @Input() parent: PersonFamilyComponent;
  display = false;
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

  ub(): UrlBuilder {
    return new UrlBuilder(this.dataset, 'families', 'children');
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
    this.display = true;
  }

  onDialogOpen(data: NewPersonDialog2Component) {
    data._data = this.nph.initNew('M', this.surname);
  }

  closeDialog() {
    this.display = false;
  }

  anchor(): string {
    return this.family.string;
  }

  refreshPerson(): void {
    this.personService.getOne(this.dataset, this.parent.person.string).subscribe(
      (person: any) => this.parent.refreshPerson());
  }
}
