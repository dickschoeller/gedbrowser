import { Component, Input } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { MatDialog } from '@angular/material';

import { SubmitterCreator } from '../../bases/submitter-creator';
import { NewSubmitterDialogComponent } from '../../components/';
import { NewSubmitterDialogData } from '../../models';
import { RefreshSubmitter } from '../../interfaces';
import { ApiSubmitter } from '../../models';
import { SubmitterService, NewSubmitterLinkService } from '../../services';
import { NewSubmitterHelper, UrlBuilder } from '../../utils';
import { SubmitterListPageComponent } from './submitter-list-page.component';

@Component({
  selector: 'app-submitter-list',
  templateUrl: './submitter-list.component.html',
  styleUrls: ['./submitter-list.component.css']
})
export class SubmitterListComponent extends SubmitterCreator implements RefreshSubmitter {
  @Input() parent: RefreshSubmitter;
  @Input() dataset: string;
  @Input() submitters: ApiSubmitter[];

  constructor(public newSubmitterLinkService: NewSubmitterLinkService,
    public dialog: MatDialog,
  ) {
    super(newSubmitterLinkService, dialog);
  }

  submitterUB(): UrlBuilder {
    return new UrlBuilder(this.dataset, 'submitters');
  }

  submitterAnchor(): string {
    return undefined;
  }

  refreshSubmitter(submitter: ApiSubmitter) {
    this.parent.refreshSubmitter(submitter);
  }
}
