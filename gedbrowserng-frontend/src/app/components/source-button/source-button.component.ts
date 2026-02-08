import { Component, Input , Inject } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';

import { HasAttributeList } from '../../interfaces';
import { SourceCreator } from '../../bases';
import { ApiObject, ApiSource, ApiAttribute, LinkDialogData, LinkItem } from '../../models';
import { SourceService } from '../../services';
import { UrlBuilder, NewSourceHelper, ApiComparators, LinkHelper, Refresher, LinkDialogLauncher, UnlinkHelper } from '../../utils';
import { MatIconButton } from '@angular/material/button';
import { MatTooltip } from '@angular/material/tooltip';
import { MatMenuTrigger, MatMenu, MatMenuItem } from '@angular/material/menu';
import { MatIcon } from '@angular/material/icon';

@Component({
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
    styles: [],
    imports: [MatIconButton, MatTooltip, MatMenuTrigger, MatIcon, MatMenu, MatMenuItem]
})
export class SourceButtonComponent extends SourceCreator {
  @Input() parent: HasAttributeList;
  @Input() dataset: string;

  constructor(
    @Inject(SourceService) @Inject(SourceService) @Inject(SourceService) @Inject(SourceService) public readonly service: SourceService,
    @Inject(MatDialog) @Inject(MatDialog) @Inject(MatDialog) public readonly dialog: MatDialog,
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
