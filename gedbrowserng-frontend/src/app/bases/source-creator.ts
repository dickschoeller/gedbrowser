import {OnChanges, OnInit} from '@angular/core';

import {NewSourceDialogData, NewSourceHelper} from '../components';
import {ApiSource} from '../models';
import {UrlBuilder} from '../utils';
import {PostRelatedSource, NewSourceLinkService} from '../services';

export abstract class SourceCreator {
  nsh = new NewSourceHelper();

  constructor(public newSourceLinkService: NewSourceLinkService) {}

  createSource(data: NewSourceDialogData): void {
    if (data != null) {
      const newSource: ApiSource = this.nsh.buildSource(data);
      this.newSourceLinkService.p(this.ub(), this.anchor(), newSource).subscribe(
        (d: ApiSource) => this.refreshSource());
    }
  }

  abstract closeDialog(): void;

  abstract ub(): UrlBuilder;

  abstract anchor(): string;

  abstract refreshSource(): void;
}
