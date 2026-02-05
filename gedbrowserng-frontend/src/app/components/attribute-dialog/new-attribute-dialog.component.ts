import { Component, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA, MatDialogTitle, MatDialogContent, MatDialogActions, MatDialogClose } from '@angular/material/dialog';

import { NewAttributeDialogData } from '../../models';
import { MatToolbar } from '@angular/material/toolbar';
import { MatIcon } from '@angular/material/icon';
import { CdkScrollable } from '@angular/cdk/scrolling';
import { MatFormField, MatLabel } from '@angular/material/form-field';
import { MatSelect, MatOption } from '@angular/material/select';
import { FormsModule } from '@angular/forms';
import { MatInput } from '@angular/material/input';
import { MatButton } from '@angular/material/button';

@Component({
    selector: 'app-new-attribute-dialog',
    template: `<div mat-dialog-title>
  <mat-toolbar color="primary"><mat-icon matListIcon>add_box</mat-icon> &nbsp; New attribute</mat-toolbar>
</div>
<div mat-dialog-content>
  <mat-form-field>
    <mat-label>Attribute type</mat-label>
    <mat-select [(ngModel)]="data.default.type">
      @for (option of data.options; track $index) {
        <mat-option [value]="option.value">{{ option.label }}</mat-option>
      }
    </mat-select>
  </mat-form-field>
  <br/>
  <mat-form-field>
    <mat-label>Text</mat-label>
    <input matInput [(ngModel)]="data.default.text">
  </mat-form-field>
  <br/>
  <mat-form-field>
    <mat-label>Date</mat-label>
    <input matInput [(ngModel)]="data.default.date">
  </mat-form-field>
  <br/>
  <mat-form-field>
    <mat-label>Place</mat-label>
    <input matInput [(ngModel)]="data.default.place">
  </mat-form-field>
  <br/>
  <mat-form-field>
    <mat-label>Note</mat-label>
    <textarea matInput [rows]="8" [cols]="100" [(ngModel)]="data.default.note"
        autoResize="autoResize"></textarea>
  </mat-form-field>
</div>
<div mat-dialog-actions>
  <span class="example-fill-remaining-space"></span>
  <button mat-button [mat-dialog-close]="data.default" cdkFocusInitial>OK</button>
  <button mat-button (click)="onNoClick()" >Cancel</button>
</div>`,
    styles: [],
    imports: [MatDialogTitle, MatToolbar, MatIcon, CdkScrollable, MatDialogContent, MatFormField, MatLabel, MatSelect, FormsModule, MatOption, MatInput, MatDialogActions, MatButton, MatDialogClose]
})
export class NewAttributeDialogComponent {
  constructor(@Inject(MatDialogRef) public dialogRef: MatDialogRef<NewAttributeDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: NewAttributeDialogData) {
  }

  onNoClick(): void {
    this.dialogRef.close();
  }
}
