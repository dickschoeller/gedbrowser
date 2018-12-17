import { Component, Input } from '@angular/core';
import { MatDialog } from '@angular/material';

import { HasAttributeList } from '../../interfaces';
import { SourceCreator } from '../../bases';
import { ApiObject, ApiSource, ApiAttribute, LinkDialogData, LinkItem } from '../../models';
import { SourceService, NewSourceLinkService, ServiceBase } from '../../services';
import { UrlBuilder, NewSourceHelper, ApiComparators, LinkHelper, Refresher, DialogHelper } from '../../utils';
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

  lh(): LinkHelper {
    return new LinkHelper((o: ApiSource) => o.title, ApiComparators.compareSources, 'sourcelink');
  }

  openLinkSourceDialog() {
    DialogHelper.openLinkDialog(this, 'Link Source', this.lh());
  }

  openUnlinkSourceDialog() {
    DialogHelper.openUnlinkDialog(this, 'Unlink Source', this.lh());
  }
}
