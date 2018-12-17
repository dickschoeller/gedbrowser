import { Component, Input } from '@angular/core';
import { MatDialog } from '@angular/material';

import { HasAttributeList } from '../../interfaces';
import { SubmitterCreator } from '../../bases';
import { ApiObject, ApiSubmitter, ApiAttribute, LinkDialogData, LinkItem } from '../../models';
import { SubmitterService, NewSubmitterLinkService } from '../../services';
import { UrlBuilder, NewSubmitterHelper, ApiComparators, LinkHelper, Refresher } from '../../utils';
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

  lh(): LinkHelper {
    return new LinkHelper((o: ApiSubmitter) => o.name, ApiComparators.compareSubmitters, 'submitterlink');
  }

  openLinkSubmitterDialog() {
    const dialogRef = this.dialog.open(
      LinkDialogComponent,
      {
        data: { name: 'Link Submitter' }
      });

    dialogRef.afterOpen().subscribe(() => {
      this.lh().onLinkDialogOpen(this.dataset, this.submitterService, dialogRef.componentInstance);
    });

    dialogRef.afterClosed().subscribe((result: LinkDialogData) => {
      if (result !== undefined) {
         this.lh().link(result, this.parent.attributes, () => this.parent.save());
      }
    });
  }

  openUnlinkSubmitterDialog() {
    const dialogRef = this.dialog.open(
      LinkDialogComponent,
      {
        data: { name: 'Unlink Submitter' }
      });

    dialogRef.afterOpen().subscribe(() => {
      this.lh().onUnlinkDialogOpen(this.dataset, this.submitterService, dialogRef.componentInstance, this.parent.attributes);
    });

    dialogRef.afterClosed().subscribe((result: LinkDialogData) => {
      if (result !== undefined) {
         this.lh().unlink(result, this.parent.attributes, () => this.parent.save());
      }
    });
  }
}
