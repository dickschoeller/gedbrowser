import { Component, Input } from '@angular/core';
import { MatDialog } from '@angular/material';

import { HasAttributeList } from '../../interfaces';
import { SourceCreator } from '../../bases';
import { ApiObject, ApiSource, ApiAttribute, LinkDialogData, LinkItem } from '../../models';
import { SourceService, NewSourceLinkService, ServiceBase } from '../../services';
import { UrlBuilder, NewSourceHelper, ApiComparators, LinkHelper, Refresher } from '../../utils';
import { LinkDialogComponent } from '../link-dialog';

@Component({
  selector: 'app-source-button',
  templateUrl: './source-button.component.html',
  styleUrls: ['./source-button.component.css']
})
export class SourceButtonComponent extends SourceCreator {
  @Input() parent: HasAttributeList;
  @Input() dataset: string;

  constructor(
    public sourceService: SourceService,
    public newSourceLinkService: NewSourceLinkService,
    public dialog: MatDialog,
  ) {
    super(newSourceLinkService, dialog);
  }

  sourceUB(): UrlBuilder {
    // This would enable creating a source but not linking.
    return new UrlBuilder(this.dataset, 'sources');
  }

  sourceAnchor(): string {
    return undefined;
  }

  refreshSource(source: ApiSource): void {
    Refresher.refresh(this.parent, 'sourcelink', source.string);
  }

  lh(): LinkHelper {
    return new LinkHelper((o: ApiSource) => o.title, ApiComparators.compareSources, 'sourcelink');
  }

  openLinkSourceDialog() {
    const dialogRef = this.dialog.open(
      LinkDialogComponent,
      {
        data: { name: 'Link Source', dataset: this.dataset }
      });

    dialogRef.afterOpen().subscribe(() => {
      this.lh().onLinkDialogOpen(this.sourceService, dialogRef.componentInstance);
    });

    dialogRef.afterClosed().subscribe((result: LinkDialogData) => {
      if (result !== undefined) {
         this.lh().link(result, this.parent.attributes, () => this.parent.save());
      }
    });
  }

  openUnlinkSourceDialog() {
    const dialogRef = this.dialog.open(
      LinkDialogComponent,
      {
        data: { name: 'Unlink Source', dataset: this.dataset }
      });

    dialogRef.afterOpen().subscribe(() => {
      this.lh().onUnlinkDialogOpen(this.sourceService, dialogRef.componentInstance, this.parent.attributes);
    });

    dialogRef.afterClosed().subscribe((result: LinkDialogData) => {
      if (result !== undefined) {
         this.lh().unlink(result, this.parent.attributes, () => this.parent.save());
      }
    });
  }
}
