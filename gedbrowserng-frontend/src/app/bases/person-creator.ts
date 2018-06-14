import { OnChanges, OnInit } from '@angular/core';

import { RefreshPerson } from '../interfaces';
import { ApiPerson, NewPersonDialogData, LinkPersonDialogData } from '../models';
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

  linkParent(data: LinkPersonDialogData) {
    this.newPersonLinkService.put(this.personUB(), this.personAnchor(), data.selectOne.person)
      .subscribe((person: ApiPerson) => { this.refreshPerson(); });
  }

  abstract personUB(): UrlBuilder;

  abstract personAnchor(): string;

  abstract refreshPerson(): void;
}
