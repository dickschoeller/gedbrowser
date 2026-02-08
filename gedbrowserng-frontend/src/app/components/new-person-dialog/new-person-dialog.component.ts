import { Component, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA, MatDialogTitle, MatDialogContent, MatDialogActions, MatDialogClose } from '@angular/material/dialog';

import { NewPersonDialogData } from '../../models';
import { MatToolbar } from '@angular/material/toolbar';
import { MatIcon } from '@angular/material/icon';
import { CdkScrollable } from '@angular/cdk/scrolling';
import { MatFormField, MatLabel } from '@angular/material/form-field';
import { MatInput } from '@angular/material/input';
import { FormsModule } from '@angular/forms';
import { MatSelect, MatOption } from '@angular/material/select';
import { MatButton } from '@angular/material/button';

@Component({
    selector: 'app-new-person-dialog',
    template: `<div mat-dialog-title>
  <mat-toolbar color="primary"><mat-icon matListIcon>person</mat-icon> &nbsp; New person</mat-toolbar>
</div>
<div mat-dialog-content>
  <mat-form-field>
    <mat-label>Name</mat-label>
    <input matInput [(ngModel)]="data.name">
  </mat-form-field>
  <br/>
  <mat-form-field>
    <mat-label>Sex</mat-label>
    <mat-select [(ngModel)]="data.sex">
      <mat-option value="">-- Unknown --</mat-option>
      <mat-option value="M">Male</mat-option>
      <mat-option value="F">Female</mat-option>
    </mat-select>
  </mat-form-field>
  <br/>
  <mat-form-field>
    <mat-label>Birth date</mat-label>
    <input matInput [(ngModel)]="data.birthDate">
  </mat-form-field>
  <br/>
  <mat-form-field>
    <mat-label>Birth place</mat-label>
    <input matInput [(ngModel)]="data.birthPlace">
  </mat-form-field>
  <br/>
  <mat-form-field>
    <mat-label>Death date</mat-label>
    <input matInput [(ngModel)]="data.deathDate">
  </mat-form-field>
  <br/>
  <mat-form-field>
    <mat-label>Death place</mat-label>
    <input matInput [(ngModel)]="data.deathPlace">
  </mat-form-field>
  <br/>
</div>
<div mat-dialog-actions>
  <span class="example-fill-remaining-space"></span>
  <button mat-button [mat-dialog-close]="data" cdkFocusInitial>OK</button>
  <button mat-button (click)="onNoClick()" >Cancel</button>
</div>`,
    styles: [],
    imports: [MatDialogTitle, MatToolbar, MatIcon, CdkScrollable, MatDialogContent, MatFormField, MatLabel, MatInput, FormsModule, MatSelect, MatOption, MatDialogActions, MatButton, MatDialogClose]
})
export class NewPersonDialogComponent {
  constructor(
    @Inject(MatDialogRef) public readonly dialogRef: MatDialogRef<NewPersonDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public readonly data: NewPersonDialogData
  ) {
  }

  onNoClick(): void {
    this.dialogRef.close();
  }
}
