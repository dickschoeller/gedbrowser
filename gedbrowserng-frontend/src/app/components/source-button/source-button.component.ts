import { Component, Input } from '@angular/core';
import { MatDialog, MatDialogRef, } from '@angular/material';

import { HasAttributeList } from '../../interfaces';
import { SourceCreator } from '../../bases';
import { ApiObject, ApiSource, ApiAttribute, LinkDialogData, LinkItem } from '../../models';
import { SourceService, NewSourceLinkService, ServiceBase } from '../../services';
import { UrlBuilder, NewSourceHelper, ApiComparators, LinkHelper, Refresher } from '../../utils';
import { LinkDialogComponent } from '../link-dialog';
import { NewSourceDialogComponent } from '../new-source-dialog';
import { NewSourceDialogData } from '../../models';

@Component({
  selector: 'app-source-button',
  templateUrl: './source-button.component.html',
  styleUrls: ['./source-button.component.css']
})
export class SourceButtonComponent extends SourceCreator {
  @Input() parent: HasAttributeList;
  @Input() dataset: string;

  data: NewSourceDialogData;
  displayLinkSourceDialog = false;
  displayUnlinkSourceDialog = false;

  constructor(
    public sourceService: SourceService,
    public newSourceLinkService: NewSourceLinkService,
    public dialog: MatDialog,
  ) {
    super(newSourceLinkService);
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

  onLinkSourceDialogClose() {
    this.displayLinkSourceDialog = false;
  }

  onLinkSourceDialogOpen(dialog: LinkDialogComponent) {
    this.lh().onLinkDialogOpen(this.sourceService, dialog);
  }

  linkSource(data: LinkDialogData) {
    this.lh().link(data, this.parent.attributes, () => this.parent.save());
  }

  onUnlinkSourceDialogClose() {
    this.displayUnlinkSourceDialog = false;
  }

  onUnlinkSourceDialogOpen(dialog: LinkDialogComponent) {
    this.lh().onUnlinkDialogOpen(this.sourceService, dialog, this.parent.attributes);
  }

  unlinkSource(data: LinkDialogData) {
    this.lh().unlink(data, this.parent.attributes, () => this.parent.save());
  }

  lh(): LinkHelper {
    return new LinkHelper((o: ApiSource) => o.title, ApiComparators.compareSources, 'sourcelink');
  }

  openSourceDialog(): void {
    const dialogRef = this.dialog.open(
      NewSourceDialogComponent,
      {
        data: { title: 'New Source', abbreviation: 'NewSource', text: '' }
      });

    dialogRef.afterClosed().subscribe(result => {
      if (result !== undefined) {
        this.data = result;
        this.createSource(this.data);
      }
    });
  }

  openLinkSourceDialog() {
    this.displayLinkSourceDialog = true;
  }

  openUnlinkSourceDialog() {
    this.displayUnlinkSourceDialog = true;
  }
}
