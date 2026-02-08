import { Component, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA, MatDialogTitle, MatDialogContent, MatDialogActions, MatDialogClose } from '@angular/material/dialog';

import { NewSourceDialogData } from '../../models';
import { MatToolbar } from '@angular/material/toolbar';
import { MatIcon } from '@angular/material/icon';
import { CdkScrollable } from '@angular/cdk/scrolling';
import { MatFormField, MatLabel } from '@angular/material/form-field';
import { MatInput } from '@angular/material/input';
import { FormsModule } from '@angular/forms';
import { MatButton } from '@angular/material/button';

@Component({
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
    styles: [],
    imports: [MatDialogTitle, MatToolbar, MatIcon, CdkScrollable, MatDialogContent, MatFormField, MatLabel, MatInput, FormsModule, MatDialogActions, MatButton, MatDialogClose]
})
export class NewSourceDialogComponent {
  constructor(
    @Inject(MatDialogRef) public readonly dialogRef: MatDialogRef<NewSourceDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public readonly data: NewSourceDialogData) {
  }

  onNoClick(): void {
    this.dialogRef.close();
  }
}
