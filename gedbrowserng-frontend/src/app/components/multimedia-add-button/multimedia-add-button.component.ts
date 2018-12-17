import { Component, Input } from '@angular/core';
import { MatDialog, MatDialogRef, } from '@angular/material';

import { HasMultimedia } from '../../interfaces';
import { ApiAttribute, MultimediaDialogData, MultimediaFileData } from '../../models';
import { StringUtil, MultimediaDialogHelper } from '../../utils';
import { MultimediaDialogComponent } from '../multimedia-dialog';

@Component({
  selector: 'app-multimedia-add-button',
  templateUrl: './multimedia-add-button.component.html',
  styleUrls: ['./multimedia-add-button.component.css']
})
export class MultimediaAddButtonComponent {
  @Input() parent: HasMultimedia;
  @Input() dataset: string;

  constructor(public dialog: MatDialog) { }

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
