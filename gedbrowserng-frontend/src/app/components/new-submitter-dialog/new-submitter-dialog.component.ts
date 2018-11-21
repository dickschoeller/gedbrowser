import { Component, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';

import { NewSubmitterDialogData } from '../../models';

@Component({
  selector: 'app-new-submitter-dialog',
  templateUrl: './new-submitter-dialog.component.html',
  styleUrls: ['./new-submitter-dialog.component.css']
})
export class NewSubmitterDialogComponent {
  constructor(public dialogRef: MatDialogRef<NewSubmitterDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: NewSubmitterDialogData) {
  }

  onNoClick(): void {
    this.dialogRef.close();
  }
}
