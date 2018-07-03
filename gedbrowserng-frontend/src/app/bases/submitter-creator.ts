import { OnChanges, OnInit } from '@angular/core';

import { ApiSubmitter, NewSubmitterDialogData } from '../models';
import { UrlBuilder } from '../utils';
import { NewSubmitterHelper } from '../utils/new-submitter-helper';
import { NewSubmitterLinkService } from '../services';

export abstract class SubmitterCreator {
  nsh = new NewSubmitterHelper();


  constructor(public newSubmitterLinkService: NewSubmitterLinkService) {}

  createSubmitter(data: NewSubmitterDialogData): void {
    if (data != null && data !== undefined) {
      const newSubmitter: ApiSubmitter = this.nsh.buildSubmitter(data);
      this.newSubmitterLinkService.p(this.submitterUB(), this.submitterAnchor(), newSubmitter)
        .subscribe((submitter: ApiSubmitter) => this.refreshSubmitter(submitter));
    }
  }

  abstract closeSubmitterDialog(): void;

  abstract submitterUB(): UrlBuilder;

  abstract submitterAnchor(): string;

  abstract refreshSubmitter(submitter: ApiSubmitter): void;

}
