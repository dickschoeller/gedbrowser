import { Component, Input } from '@angular/core';
import { MatDialog } from '@angular/material';

import { HasAttributeList } from '../../interfaces';
import { SourceCreator } from '../../bases';
import { ApiObject, ApiSource, ApiAttribute, LinkDialogData, LinkItem } from '../../models';
import { SourceService, NewSourceLinkService, ServiceBase } from '../../services';
import { UrlBuilder, NewSourceHelper, ApiComparators, LinkHelper, Refresher, LinkDialogLauncher, UnlinkHelper } from '../../utils';

@Component({
  selector: 'app-source-button',
  templateUrl: './source-button.component.html',
  styleUrls: ['./source-button.component.css']
})
export class SourceButtonComponent extends SourceCreator {
  @Input() parent: HasAttributeList;
  @Input() dataset: string;

  constructor(
    public service: SourceService,
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

  openLinkSourceDialog() {
    const lh = new LinkHelper((o: ApiSource) => o.title, ApiComparators.compareSources, 'sourcelink');
    LinkDialogLauncher.openDialog(this, 'Link Source', lh);
  }

  openUnlinkSourceDialog() {
    const lh = new UnlinkHelper((o: ApiSource) => o.title, ApiComparators.compareSources, 'sourcelink');
    LinkDialogLauncher.openDialog(this, 'Unlink Source', lh);
  }
}
