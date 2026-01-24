import { Component, Input } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';

import { HasAttributeList } from '../../interfaces';
import { SourceCreator } from '../../bases';
import { ApiObject, ApiSource, ApiAttribute, LinkDialogData, LinkItem } from '../../models';
import { SourceService } from '../../services';
import { UrlBuilder, NewSourceHelper, ApiComparators, LinkHelper, Refresher, LinkDialogLauncher, UnlinkHelper } from '../../utils';

@Component({
  standalone: false,
  selector: 'app-source-button',
  template: `<span>
  <button mat-icon-button matTooltip="Source" [matMenuTriggerFor]="sourceMenu" color="primary">
    <mat-icon matListIcon>book</mat-icon></button>
</span>

<mat-menu #sourceMenu="matMenu" [overlapTrigger]="false">
  <button mat-menu-item (click)="openCreateSourceDialog()"><mat-icon>library_add</mat-icon> Add source</button>
  <button mat-menu-item (click)="openLinkSourceDialog()"><mat-icon>link</mat-icon> Link source</button>
  <button mat-menu-item (click)="openUnlinkSourceDialog()"><mat-icon color="warn">link_off</mat-icon> Unlink source</button>
</mat-menu>`,
    styles: []
})
export class SourceButtonComponent extends SourceCreator {
  @Input() parent: HasAttributeList;
  @Input() dataset: string;

  constructor(
    public service: SourceService,
    public dialog: MatDialog,
  ) {
    super(service, dialog);
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
