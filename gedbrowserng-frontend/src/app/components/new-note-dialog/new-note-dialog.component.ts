import { Component, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';

import { NewNoteDialogData } from '../../models';

@Component({
  standalone: false,
  selector: 'app-new-note-dialog',
  template: `<div mat-dialog-title>
  <mat-toolbar color="primary"><mat-icon matListIcon>comment</mat-icon> &nbsp; New note</mat-toolbar>
</div>
<div mat-dialog-content>
  <mat-form-field>
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
export class NewNoteDialogComponent {
  constructor(public dialogRef: MatDialogRef<NewNoteDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: NewNoteDialogData) {
  }

  onNoClick(): void {
    this.dialogRef.close();
  }
}
