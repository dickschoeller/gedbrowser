import { Component, Input } from '@angular/core';
import { MatDialog, MatDialogRef, } from '@angular/material';

import { Saveable } from '../../interfaces';
import { ApiAttribute, MultimediaDialogData } from '../../models';
import { MultimediaDialogHelper } from '../../utils';
import { MultimediaDialogComponent } from '../multimedia-dialog';

@Component({
  selector: 'app-multimedia-edit-button',
  templateUrl: './multimedia-edit-button.component.html',
  styleUrls: ['./multimedia-edit-button.component.css']
})
export class MultimediaEditButtonComponent {
  @Input() dataset: string;
  @Input() parent: Saveable;
  @Input() attributes: Array<ApiAttribute>;
  @Input() index;

  constructor(public dialog: MatDialog) { }

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
