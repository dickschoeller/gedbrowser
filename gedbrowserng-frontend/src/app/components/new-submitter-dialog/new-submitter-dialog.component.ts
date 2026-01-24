import { Component, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';

import { NewSubmitterDialogData } from '../../models';

@Component({
  standalone: false,
  selector: 'app-new-submitter-dialog',
  template: `<div mat-dialog-title>
  <mat-toolbar color="primary"><mat-icon matListIcon>contact_mail</mat-icon> &nbsp; New submitter</mat-toolbar>
</div>
<div mat-dialog-content>
  <mat-form-field>
    <input matInput placeHolder="Name" [(ngModel)]="data.name">
  </mat-form-field>
</div>
<div mat-dialog-actions>
  <span class="example-fill-remaining-space"></span>
  <button mat-button [mat-dialog-close]="data" cdkFocusInitial>OK</button>
  <button mat-button (click)="onNoClick()" >Cancel</button>
</div>`,
    styles: []
})
export class NewSubmitterDialogComponent {
  constructor(public dialogRef: MatDialogRef<NewSubmitterDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: NewSubmitterDialogData) {
  }

  onNoClick(): void {
    this.dialogRef.close();
  }
}
