import { Component, Input } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';

import { HasAttributeList } from '../../interfaces';
import { SubmitterCreator } from '../../bases';
import { ApiObject, ApiSubmitter, ApiAttribute, LinkDialogData, LinkItem } from '../../models';
import { SubmitterService } from '../../services';
import { UrlBuilder, NewSubmitterHelper, ApiComparators, LinkHelper, Refresher, LinkDialogLauncher, UnlinkHelper } from '../../utils';

@Component({
  standalone: false,
  selector: 'app-submitter-button',
  template: `<span>
  <button mat-icon-button matTooltip="Submitter" [matMenuTriggerFor]="submitterMenu" color="primary">
    <mat-icon matListIcon>mail</mat-icon></button>
</span>

<mat-menu #submitterMenu="matMenu" [overlapTrigger]="false">
  <button mat-menu-item (click)="openCreateSubmitterDialog()"><mat-icon>add_circle</mat-icon> Add submitter</button>
  <button mat-menu-item (click)="openLinkSubmitterDialog()"><mat-icon>link</mat-icon> Link submitter</button>
  <button mat-menu-item (click)="openUnlinkSubmitterDialog()"><mat-icon color="warn">link_off</mat-icon> Unlink submitter</button>
</mat-menu>`,
    styles: []
})
export class SubmitterButtonComponent extends SubmitterCreator {
  @Input() parent: HasAttributeList;
  @Input() dataset: string;

  constructor(
    public service: SubmitterService,
    public dialog: MatDialog,
  ) {
    super(service, dialog);
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

  openLinkSubmitterDialog() {
    const lh = new LinkHelper((o: ApiSubmitter) => o.name, ApiComparators.compareSubmitters, 'submitterlink');
    LinkDialogLauncher.openDialog(this, 'Link Submitter', lh);
  }

  openUnlinkSubmitterDialog() {
    const lh = new UnlinkHelper((o: ApiSubmitter) => o.name, ApiComparators.compareSubmitters, 'submitterlink');
    LinkDialogLauncher.openDialog(this, 'Unlink Submitter', lh);
  }
}
