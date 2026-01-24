import { Component, Input , Inject } from '@angular/core';
import { MatDialog, MatDialogRef, } from '@angular/material/dialog';

import { HasMultimedia } from '../../interfaces';
import { ApiAttribute, MultimediaDialogData, MultimediaFileData } from '../../models';
import { StringUtil, MultimediaDialogHelper } from '../../utils';
import { MultimediaDialogComponent } from '../multimedia-dialog';

@Component({
  standalone: false,
  selector: 'app-multimedia-add-button',
  template: `<span>
  <button mat-icon-button matTooltip="Multimedia" [matMenuTriggerFor]="multimediaMenu" color="primary">
    <mat-icon matListIcon>image</mat-icon></button>
</span>

<mat-menu #multimediaMenu="matMenu" [overlapTrigger]="false">
  <button mat-menu-item (click)="openMultimediaDialog()"><mat-icon>add_photo_alternate</mat-icon> Add multimedia</button>
</mat-menu>`,
    styles: []
})
export class MultimediaAddButtonComponent {
  @Input() parent: HasMultimedia;
  @Input() dataset: string;

  constructor(@Inject(MatDialog) @Inject(MatDialog) @Inject(MatDialog) @Inject(MatDialog) public dialog: MatDialog) { }

  create(data: MultimediaDialogData) {
    const attribute: ApiAttribute = MultimediaDialogHelper.buildMultimediaAttribute(data);
    this.parent.multimedia.push(attribute);
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
