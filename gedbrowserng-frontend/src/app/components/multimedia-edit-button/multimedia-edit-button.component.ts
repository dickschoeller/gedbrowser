import { Component, Input , Inject } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';

import { Saveable } from '../../interfaces';
import { ApiAttribute, MultimediaDialogData } from '../../models';
import { MultimediaDialogHelper } from '../../utils';
import { MultimediaDialogComponent } from '../multimedia-dialog';
import { MatIconButton } from '@angular/material/button';
import { MatTooltip } from '@angular/material/tooltip';
import { MatIcon } from '@angular/material/icon';

@Component({
    selector: 'app-multimedia-edit-button',
    template: `<button mat-icon-button matTooltip="Edit multimedia attribute" color="primary" (click)="edit()">
  <mat-icon matListIcon>edit</mat-icon></button>`,
    styles: [],
    imports: [MatIconButton, MatTooltip, MatIcon]
})
export class MultimediaEditButtonComponent {
  @Input() dataset: string;
  @Input() parent: Saveable;
  @Input() attributes: Array<ApiAttribute>;
  @Input() index;

  constructor(@Inject(MatDialog) @Inject(MatDialog) @Inject(MatDialog) @Inject(MatDialog) public readonly dialog: MatDialog) { }

  edit(): void {
    const dialogRef = this.dialog.open(
      MultimediaDialogComponent,
      {
        data: MultimediaDialogHelper.buildMultimediaDialogData(this.attributes, this.index)
      });

    dialogRef.afterClosed().subscribe((result: MultimediaDialogData) => {
      if (result !== undefined) {
        this.update(result);
      }
    });
  }

  update(data: MultimediaDialogData): void {
    this.attributes.splice(this.index, 1, MultimediaDialogHelper.buildMultimediaAttribute(data));
    this.parent.save();
  }
}
