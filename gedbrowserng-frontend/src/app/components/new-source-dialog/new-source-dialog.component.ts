import { Component, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';

import { NewSourceDialogData } from '../../models';

@Component({
  standalone: false,
  selector: 'app-new-source-dialog',
  templateUrl: './new-source-dialog.component.html',
  styleUrls: ['./new-source-dialog.component.css']
})
export class NewSourceDialogComponent {
  constructor(public dialogRef: MatDialogRef<NewSourceDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: NewSourceDialogData) {
  }

  onNoClick(): void {
    this.dialogRef.close();
  }
}
