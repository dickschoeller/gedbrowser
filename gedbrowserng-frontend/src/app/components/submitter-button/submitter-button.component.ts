import { Component, Input } from '@angular/core';
import { MatDialog, MatDialogRef, } from '@angular/material';

import { HasAttributeList } from '../../interfaces';
import { SubmitterCreator } from '../../bases';
import { ApiObject, ApiSubmitter, ApiAttribute, LinkDialogData, LinkItem } from '../../models';
import { SubmitterService, NewSubmitterLinkService } from '../../services';
import { UrlBuilder, NewSubmitterHelper, ApiComparators, LinkHelper, Refresher } from '../../utils';
import { LinkDialogComponent } from '../link-dialog';
import { NewSubmitterDialogComponent } from '../new-submitter-dialog';
import { NewSubmitterDialogData } from '../../models';

@Component({
  selector: 'app-submitter-button',
  templateUrl: './submitter-button.component.html',
  styleUrls: ['./submitter-button.component.css']
})
export class SubmitterButtonComponent extends SubmitterCreator {
  @Input() parent: HasAttributeList;
  @Input() dataset: string;

  displayLinkSubmitterDialog = false;
  displayUnlinkSubmitterDialog = false;

  constructor(
    public submitterService: SubmitterService,
    public newSubmitterLinkService: NewSubmitterLinkService,
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

  refreshSubmitter(submitter: ApiSubmitter): void {
    Refresher.refresh(this.parent, 'submitterLink', submitter.string);
  }

  onLinkSubmitterDialogClose() {
    this.displayLinkSubmitterDialog = false;
  }

  onLinkSubmitterDialogOpen(dialog: LinkDialogComponent) {
    this.lh().onLinkDialogOpen(this.submitterService, dialog);
  }

  linkSubmitter(data: LinkDialogData) {
    this.lh().link(data, this.parent.attributes, () => this.parent.save());
  }

  onUnlinkSubmitterDialogClose() {
    this.displayUnlinkSubmitterDialog = false;
  }

  onUnlinkSubmitterDialogOpen(dialog: LinkDialogComponent) {
    this.lh().onUnlinkDialogOpen(this.submitterService, dialog, this.parent.attributes);
  }

  unlinkSubmitter(data: LinkDialogData) {
    this.lh().unlink(data, this.parent.attributes, () => this.parent.save());
  }

  lh(): LinkHelper {
    return new LinkHelper((o: ApiSubmitter) => o.name, ApiComparators.compareSubmitters, 'submitterlink');
  }

  openLinkSubmitterDialog() {
    this.displayLinkSubmitterDialog = true;
  }

  openUnlinkSubmitterDialog() {
    this.displayUnlinkSubmitterDialog = true;
  }
}
