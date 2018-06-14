import { OnChanges, OnInit } from '@angular/core';

import { RefreshPerson } from '../interfaces';
import { ApiPerson, NewPersonDialogData } from '../models';
import { NewPersonHelper, UrlBuilder } from '../utils';
import { PostRelatedPerson, NewPersonLinkService } from '../services';

export abstract class PersonCreator implements RefreshPerson {
  nph = new NewPersonHelper();

  constructor(public newPersonLinkService: NewPersonLinkService) {}

  createPerson(data: NewPersonDialogData): void {
    if (data != null) {
      const newPerson: ApiPerson = this.nph.buildPerson(data);
      this.newPersonLinkService.post(this.personUB(), this.personAnchor(), newPerson)
        .subscribe((d: ApiPerson) => this.refreshPerson());
    }
  }

  abstract personUB(): UrlBuilder;

  abstract personAnchor(): string;

  abstract refreshPerson(): void;
}
