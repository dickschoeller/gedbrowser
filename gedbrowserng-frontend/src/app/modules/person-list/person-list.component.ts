import {Component, Input} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';

import {PersonCreator} from '../../bases';
import {
  NewPersonDialogComponent
} from '../../components';
import {ApiPerson, NewPersonDialogData} from '../../models';
import {PersonService, NewPersonLinkService} from '../../services';
import {UrlBuilder, NewPersonHelper} from '../../utils';
import {PersonListPageComponent} from './person-list-page.component';

@Component({
  selector: 'app-person-list',
  templateUrl: './person-list.component.html',
  styleUrls: ['./person-list.component.css']
})
export class PersonListComponent extends PersonCreator {
  @Input() p: PersonListPageComponent;
  @Input() dataset: string;
  @Input() persons: ApiPerson[];
  displayPersonDialog = false;

  constructor(public newPersonLinkService: NewPersonLinkService) {
    super(newPersonLinkService);
  }

  openCreatePersonDialog(): void {
    this.displayPersonDialog = true;
  }
  onDialogOpen(data: NewPersonDialogComponent) {
    data._data = this.nph.initNew('M', '');
  }


  personUB(): UrlBuilder {
    return new UrlBuilder(this.dataset, 'persons');
  }

  closePersonDialog(): void {
    this.displayPersonDialog = false;
  }

  personAnchor(): string {
    return undefined;
  }

  refreshPerson() {
    this.p.refreshPerson();
  }
}
