import {Component, Input} from '@angular/core';

import {NewPersonDialogComponent} from '../../components';
import {ApiAttribute, ApiPerson, ApiFamily, NewPersonDialogData} from '../../models';
import {UrlBuilder} from '../../utils';
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
  surname: string;

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
}
