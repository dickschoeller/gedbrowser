import { SubmitterCreator } from '../../bases';
import { Component, Input } from '@angular/core';
import { MenuItem, SelectItem } from 'primeng/api';

import { HasAttributeList } from '../../interfaces';
import { ApiObject, ApiSubmitter, ApiAttribute, LinkDialogData, LinkItem } from '../../models';
import { SubmitterService, NewSubmitterLinkService } from '../../services';
import { UrlBuilder, NewSubmitterHelper, ApiComparators, LinkHelper, Refresher } from '../../utils';
import { LinkDialogComponent } from '../link-dialog';
import { NewSubmitterDialogComponent } from '../new-submitter-dialog';

@Component({
  selector: 'app-submitter-button',
  templateUrl: './submitter-button.component.html',
  styleUrls: ['./submitter-button.component.css']
})
export class SubmitterButtonComponent extends SubmitterCreator {
//  @Input() parentObject: ApiObject;
  @Input() parent: HasAttributeList;
  @Input() dataset: string;

  displaySubmitterDialog = false;
  displayLinkSubmitterDialog = false;
  displayUnlinkSubmitterDialog = false;

  constructor(
    public submitterService: SubmitterService,
    public newSubmitterLinkService: NewSubmitterLinkService,
  ) {
    super(newSubmitterLinkService);
  }

  submitterUB(): UrlBuilder {
    return new UrlBuilder(this.dataset, 'submitters');
  }

  submitterAnchor(): string {
    return undefined;
  }

  closeSubmitterDialog(): void {
    this.displaySubmitterDialog = false;
  }

  refreshSubmitter(submitter: ApiSubmitter): void {
    Refresher.refresh(this.parent, 'submitterLink', submitter.string);
  }

  onSubmitterDialogClose() {
    this.displaySubmitterDialog = false;
  }

  onSubmitterDialogOpen(data: NewSubmitterDialogComponent) {
    if (data !== undefined) {
      data._data = NewSubmitterHelper.initNew('New Submitter');
    }
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

  openSubmitterDialog() {
    this.displaySubmitterDialog = true;
  }

  openLinkSubmitterDialog() {
    this.displayLinkSubmitterDialog = true;
  }

  openUnlinkSubmitterDialog() {
    this.displayUnlinkSubmitterDialog = true;
  }
}
