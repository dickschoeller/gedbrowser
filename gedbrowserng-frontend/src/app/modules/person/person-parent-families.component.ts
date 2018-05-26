import {Component, Input} from '@angular/core';

import {NewPersonDialogData, NewPersonDialog2Component} from '../../components';
import {ApiAttribute, ApiPerson, ApiFamily} from '../../models';
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
  display = false;
  surname: string;

  constructor(private personService: PersonService,
    newPersonLinkService: NewPersonLinkService) {
    super(newPersonLinkService);
  }

  init(): void {
    this.surname = this.person.surname;
  }

  createParentFamily() {
    this.display = true;
  }

  ub(): UrlBuilder {
    return new UrlBuilder(this.dataset, 'persons', 'parents');
  }

  onDialogOpen(dialog: NewPersonDialog2Component) {
    dialog._data = this.nph.initNew('M', this.person.surname);
  }

  closeDialog() {
    this.display = false;
  }

  anchor () {
    return this.person.string;
  }

  refreshPerson() {
    this.personService.getOne(this.dataset, this.person.string).subscribe(
      (data: ApiPerson) => this.parent.person = data);
  }
}
