import { SourceCreator } from '../../bases';
import { Component, Input } from '@angular/core';
import { MenuItem, SelectItem } from 'primeng/api';

import { HasAttributeList } from '../../interfaces';
import { ApiObject, ApiSource, ApiAttribute, LinkDialogData, LinkItem } from '../../models';
import { SourceService, NewSourceLinkService, ServiceBase } from '../../services';
import { UrlBuilder, NewSourceHelper, ApiComparators, LinkHelper, Refresher } from '../../utils';
import { LinkDialogComponent } from '../link-dialog';
import { NewSourceDialogComponent } from '../new-source-dialog';

@Component({
  selector: 'app-sources',
  templateUrl: './sources.component.html',
  styleUrls: ['./sources.component.css']
})
export class SourcesComponent extends SourceCreator {
  @Input() parent: HasAttributeList;
  @Input() dataset: string;

  displaySourceDialog = false;
  displayLinkSourceDialog = false;
  displayUnlinkSourceDialog = false;

  sourcemenuitems: MenuItem[] = [
    {
      label: 'Add source',
      icon: 'fa-plus-circle',
      command: (event: Event) => this.displaySourceDialog = true
    },
    {
      label: 'Link source',
      icon: 'fa-link',
      command: (event: Event) => this.displayLinkSourceDialog = true
    },
    {
      label: 'Unlink source',
      icon: 'fa-unlink',
      command: (event: Event) => this.displayUnlinkSourceDialog = true
    },
  ];

  constructor(
    public sourceService: SourceService,
    public newSourceLinkService: NewSourceLinkService,
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

  closeSourceDialog(): void {
    this.displaySourceDialog = false;
  }

  refreshSource(source: ApiSource): void {
    Refresher.refresh(this.parent, 'sourceLink', source.string);
  }

  onSourceDialogClose() {
    this.displaySourceDialog = false;
  }

  onSourceDialogOpen(data: NewSourceDialogComponent) {
    if (data !== undefined) {
      data._data = NewSourceHelper.initNew('New Source');
    }
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
}
