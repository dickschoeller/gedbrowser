import {Component, Input} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';

import {
  NewPersonDialog2Component,
  NewPersonDialogData,
  NewPersonHelper
} from '../../components';
import {ApiPerson} from '../../models';
import {PersonService, NewPersonLinkService} from '../../services';
import {UrlBuilder} from '../../utils';
import { PersonListPageComponent } from './person-list-page.component';

@Component({
  selector: 'app-person-list',
  templateUrl: './person-list.component.html',
  styleUrls: ['./person-list.component.css']
})
export class PersonListComponent {
  @Input() p: PersonListPageComponent;
  @Input() dataset: string;
  @Input() persons: ApiPerson[];
  display = false;
  nph = new NewPersonHelper();

  constructor(private newPersonLinkService: NewPersonLinkService) {}

  ub(): UrlBuilder {
    return new UrlBuilder(this.dataset, 'persons');
  }

  openCreatePersonDialog(): void {
    this.display = true;
  }

  closeDialog(): void {
    this.display = false;
  }

  onDialogOpen(data: NewPersonDialog2Component) {
    data._data = this.nph.initNew('M', '');
  }

  anchor(): string {
    return undefined;
  }

  createPerson(data: NewPersonDialogData): void {
    if (data != null) {
      const newPerson: ApiPerson = this.nph.buildPerson(data);
      this.newPersonLinkService.p(this.ub(), this.anchor(), newPerson).subscribe(
        (d: ApiPerson) => this.p.refreshPerson());
    }
  }
}
