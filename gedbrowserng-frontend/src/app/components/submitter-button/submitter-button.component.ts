import { Component, Input } from '@angular/core';
import { MatDialog } from '@angular/material';

import { HasAttributeList } from '../../interfaces';
import { SubmitterCreator } from '../../bases';
import { ApiObject, ApiSubmitter, ApiAttribute, LinkDialogData, LinkItem } from '../../models';
import { SubmitterService, NewSubmitterLinkService } from '../../services';
import { UrlBuilder, NewSubmitterHelper, ApiComparators, LinkHelper, Refresher, DialogHelper } from '../../utils';
import { LinkDialogComponent } from '../link-dialog';

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

  lh(): LinkHelper {
    return new LinkHelper((o: ApiSubmitter) => o.name, ApiComparators.compareSubmitters, 'submitterlink');
  }

  openLinkSubmitterDialog() {
    DialogHelper.openLinkDialog(this, 'Link Submitter', this.lh());
  }

  openUnlinkSubmitterDialog() {
    DialogHelper.openUnlinkDialog(this, 'Unlink Submitter', this.lh());
  }
}
