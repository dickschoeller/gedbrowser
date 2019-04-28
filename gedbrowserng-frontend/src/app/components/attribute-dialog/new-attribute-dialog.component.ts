import { Component, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';

import { NewAttributeDialogData } from '../../models';

@Component({
  selector: 'app-new-attribute-dialog',
  templateUrl: './new-attribute-dialog.component.html',
  styleUrls: ['./new-attribute-dialog.component.css']
})
export class NewAttributeDialogComponent {
  constructor(public dialogRef: MatDialogRef<NewAttributeDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: NewAttributeDialogData) {
  }

  onNoClick(): void {
    this.dialogRef.close();
  }
}
