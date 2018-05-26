import {OnChanges, OnInit} from '@angular/core';

import {NewPersonDialogData, NewPersonHelper} from '../components';
import {ApiPerson} from '../models';
import {UrlBuilder} from '../utils';
import {PostRelatedPerson, NewPersonLinkService} from '../services';

export abstract class PersonCreator  {
  nph = new NewPersonHelper();

  constructor(public newPersonLinkService: NewPersonLinkService) {}

  createPerson(data: NewPersonDialogData): void {
    if (data != null) {
      const newPerson: ApiPerson = this.nph.buildPerson(data);
      this.newPersonLinkService.p(this.ub(), this.anchor(), newPerson).subscribe(
        (d: ApiPerson) => this.refreshPerson());
    }
  }

  abstract closeDialog(): void;

  abstract ub(): UrlBuilder;

  abstract anchor(): string;

  abstract refreshPerson(): void;
}
