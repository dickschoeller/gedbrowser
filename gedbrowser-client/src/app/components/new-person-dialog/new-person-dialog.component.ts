import {Component, Inject, EventEmitter, Output} from '@angular/core';
import {MatDialog, MatDialogRef, MAT_DIALOG_DATA} from '@angular/material';

import {NewPersonDialogData} from './new-person-dialog-data';

@Component({
  selector: 'app-new-person-dialog',
  templateUrl: './new-person-dialog.component.html',
  styleUrls: ['./new-person-dialog.component.css']
})
export class NewPersonDialogComponent {
  @Output() onOK = new EventEmitter<NewPersonDialogData>();

  constructor(public dialogRef: MatDialogRef<NewPersonDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: NewPersonDialogData) {}

  onClickOK() {
    this.onOK.emit(this.data);
  }

  onNoClick(): void {
    this.dialogRef.close();
  }
}
