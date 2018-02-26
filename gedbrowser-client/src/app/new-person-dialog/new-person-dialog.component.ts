import { NewPersonDialogData } from './new-person-dialog-data';
import {Component, Inject} from '@angular/core';
import {MatDialog, MatDialogRef, MAT_DIALOG_DATA} from '@angular/material';

@Component({
  selector: 'app-new-person-dialog',
  templateUrl: './new-person-dialog.component.html',
  styleUrls: ['./new-person-dialog.component.css']
})
export class NewPersonDialogComponent {

  constructor(public dialogRef: MatDialogRef<NewPersonDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: NewPersonDialogData) {}

  onNoClick(): void {
    this.dialogRef.close();
  }
}
