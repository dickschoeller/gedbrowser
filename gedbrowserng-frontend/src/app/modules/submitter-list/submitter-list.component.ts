import { Component, Input } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { SubmitterCreator } from '../../bases/submitter-creator';
import { NewSubmitterDialogComponent } from '../../components/';
import { RefreshSubmitter } from '../../interfaces';
import { ApiSubmitter, NewSubmitterDialogData } from '../../models';
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
  display = false;

  constructor(public newSubmitterLinkService: NewSubmitterLinkService) {
    super(newSubmitterLinkService);
  }

  submitterUB(): UrlBuilder {
    return new UrlBuilder(this.dataset, 'submitters');
  }

  openCreateSubmitterDialog(): void {
    this.display = true;
  }

  closeSubmitterDialog(): void {
    this.display = false;
  }

  onDialogOpen(data: NewSubmitterDialogComponent) {
    data._data = this.nsh.initNew('New Submitter');
  }

  submitterAnchor(): string {
    return undefined;
  }

  refreshSubmitter(submitter: ApiSubmitter) {
    this.parent.refreshSubmitter(submitter);
  }
}
