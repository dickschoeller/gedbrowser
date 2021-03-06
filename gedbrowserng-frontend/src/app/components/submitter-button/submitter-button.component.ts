import { Component, Input } from '@angular/core';
import { MatDialog } from '@angular/material';

import { HasAttributeList } from '../../interfaces';
import { SubmitterCreator } from '../../bases';
import { ApiObject, ApiSubmitter, ApiAttribute, LinkDialogData, LinkItem } from '../../models';
import { SubmitterService } from '../../services';
import { UrlBuilder, NewSubmitterHelper, ApiComparators, LinkHelper, Refresher, LinkDialogLauncher, UnlinkHelper } from '../../utils';

@Component({
  selector: 'app-submitter-button',
  templateUrl: './submitter-button.component.html',
  styleUrls: ['./submitter-button.component.css']
})
export class SubmitterButtonComponent extends SubmitterCreator {
  @Input() parent: HasAttributeList;
  @Input() dataset: string;

  constructor(
    public service: SubmitterService,
    public dialog: MatDialog,
  ) {
    super(service, dialog);
  }

  submitterUB(): UrlBuilder {
    return new UrlBuilder(this.dataset, 'submitters');
  }

  submitterAnchor(): string {
    return undefined;
  }

  refreshSubmitter(submitter: ApiSubmitter): void {
    Refresher.refresh(this.parent, 'submitterLink', submitter.string);
  }

  openLinkSubmitterDialog() {
    const lh = new LinkHelper((o: ApiSubmitter) => o.name, ApiComparators.compareSubmitters, 'submitterlink');
    LinkDialogLauncher.openDialog(this, 'Link Submitter', lh);
  }

  openUnlinkSubmitterDialog() {
    const lh = new UnlinkHelper((o: ApiSubmitter) => o.name, ApiComparators.compareSubmitters, 'submitterlink');
    LinkDialogLauncher.openDialog(this, 'Unlink Submitter', lh);
  }
}
