import {OnChanges, OnInit} from '@angular/core';

import {ApiPerson, NewPersonDialogData} from '../models';
import {NewPersonHelper, UrlBuilder} from '../utils';
import {PostRelatedPerson, NewPersonLinkService} from '../services';

export abstract class PersonCreator  {
  nph = new NewPersonHelper();

  constructor(public newPersonLinkService: NewPersonLinkService) {}

  createPerson(data: NewPersonDialogData): void {
    if (data != null) {
      const newPerson: ApiPerson = this.nph.buildPerson(data);
      this.newPersonLinkService.p(this.personUB(), this.personAnchor(), newPerson)
        .subscribe((d: ApiPerson) => this.refreshPerson());
    }
  }

  abstract closePersonDialog(): void;

  abstract personUB(): UrlBuilder;

  abstract personAnchor(): string;

  abstract refreshPerson(): void;
}
