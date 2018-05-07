import {NewPersonDialogData, NewPersonHelper} from '../../components/new-person-dialog';
import {OnChanges, OnInit} from '@angular/core';

import {ApiPerson} from '../../models';
import {UrlBuilder} from '../../utils';
import {PostRelatedPerson, NewPersonLinkService} from '../../services';

export abstract class PersonCreator implements OnInit, OnChanges {
  nph = new NewPersonHelper();

  constructor(public newPersonLinkService: NewPersonLinkService) {}

  ngOnInit(): void {
    this.init();
  }

  ngOnChanges(): void {
    this.init();
  }

  createPerson(data: NewPersonDialogData): void {
    if (data != null) {
      const newPerson: ApiPerson = this.nph.buildPerson(data);
      this.newPersonLinkService.p(this.ub(), this.anchor(), newPerson).subscribe(
        (d: ApiPerson) => this.refreshPerson());
    }
  }

  abstract init(): void;

  abstract closeDialog(): void;

  abstract ub(): UrlBuilder;

  abstract anchor(): string;

  abstract refreshPerson(): void;
}
