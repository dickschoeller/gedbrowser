import { Component, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';

import { NewSourceDialogData } from '../../models';

@Component({
  standalone: false,
  selector: 'app-new-source-dialog',
  template: `<div mat-dialog-title>
  <mat-toolbar color="primary"><mat-icon matListIcon>book</mat-icon> &nbsp; New source</mat-toolbar>
</div>
<div mat-dialog-content>
  <mat-form-field>
    <mat-label>Title</mat-label>
    <input matInput [(ngModel)]="data.title">
  </mat-form-field>
  <br/>
  <mat-form-field>
    <mat-label>Abbreviation</mat-label>
    <input matInput [(ngModel)]="data.abbreviation">
  </mat-form-field>
  <br/>
  <mat-form-field>
    <mat-label>Text</mat-label>
    <textarea matInput [rows]="8" [cols]="100" [(ngModel)]="data.text"
        autoResize="autoResize"></textarea>
  </mat-form-field>
</div>
<div mat-dialog-actions>
  <span class="example-fill-remaining-space"></span>
  <button mat-button [mat-dialog-close]="data" cdkFocusInitial>OK</button>
  <button mat-button (click)="onNoClick()" >Cancel</button>
</div>`,
    styles: []
})
export class NewSourceDialogComponent {
  constructor(@Inject(MatDialogRef<NewSourceDialogComponent>) @Inject(MatDialogRef<NewSourceDialogComponent>) @Inject(MatDialogRef<NewSourceDialogComponent>) public dialogRef: MatDialogRef<NewSourceDialogComponent>,
    @Inject(MAT_DIALOG_DATA) @Inject(NewSourceDialogData) @Inject(NewSourceDialogData) @Inject(NewSourceDialogData) public data: NewSourceDialogData) {
  }

  onNoClick(): void {
    this.dialogRef.close();
  }
}
