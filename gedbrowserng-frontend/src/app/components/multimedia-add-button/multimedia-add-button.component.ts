import { Component, Input , Inject } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';

import { HasMultimedia } from '../../interfaces';
import { ApiAttribute, MultimediaDialogData } from '../../models';
import { MultimediaDialogHelper } from '../../utils';
import { MultimediaDialogComponent } from '../multimedia-dialog';
import { MatIconButton } from '@angular/material/button';
import { MatTooltip } from '@angular/material/tooltip';
import { MatMenuTrigger, MatMenu, MatMenuItem } from '@angular/material/menu';
import { MatIcon } from '@angular/material/icon';

@Component({
    selector: 'app-multimedia-add-button',
    template: `<span>
  <button mat-icon-button matTooltip="Multimedia" [matMenuTriggerFor]="multimediaMenu" color="primary">
    <mat-icon matListIcon>image</mat-icon></button>
</span>

<mat-menu #multimediaMenu="matMenu" [overlapTrigger]="false">
  <button mat-menu-item (click)="openMultimediaDialog()"><mat-icon>add_photo_alternate</mat-icon> Add multimedia</button>
</mat-menu>`,
    styles: [],
    imports: [MatIconButton, MatTooltip, MatMenuTrigger, MatIcon, MatMenu, MatMenuItem]
})
export class MultimediaAddButtonComponent {
  @Input() parent: HasMultimedia;
  @Input() dataset: string;

  constructor(@Inject(MatDialog) public readonly dialog: MatDialog) { }

  create(data: MultimediaDialogData) {
    const attribute: ApiAttribute = MultimediaDialogHelper.buildMultimediaAttribute(data);
    this.parent.multimedia = this.parent.multimedia ?? [];
    this.parent.multimedia.push(attribute);
    this.parent.refreshMultimedia?.();
    this.parent.save();
  }

  openMultimediaDialog() {
    const dialogRef = this.dialog.open(
      MultimediaDialogComponent,
      {
        data: { title: 'Title', files: [ { fileUrl: '' } ] }
      });

    dialogRef.afterClosed().subscribe((result: MultimediaDialogData) => {
      if (result !== undefined) {
        this.create(result);
      }
    });
  }
}
