import { Component, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA, MatDialogTitle, MatDialogContent, MatDialogActions, MatDialogClose } from '@angular/material/dialog';
import { MatButton } from '@angular/material/button';
import { MatToolbar } from '@angular/material/toolbar';
import { MatIcon } from '@angular/material/icon';
import { CdkScrollable } from '@angular/cdk/scrolling';

export interface ConfirmDialogData {
    message: string;
}

@Component({
    selector: 'app-confirm-dialog',
    template: `<div mat-dialog-title>
  <mat-toolbar color="warn"><mat-icon matListIcon>warning</mat-icon> &nbsp; Confirm Delete</mat-toolbar>
</div>
<div mat-dialog-content>
  <p>{{ data.message }}</p>
</div>
<div mat-dialog-actions>
  <span class="example-fill-remaining-space"></span>
  <button mat-button [mat-dialog-close]="true" cdkFocusInitial>OK</button>
  <button mat-button (click)="onNoClick()">Cancel</button>
</div>`,
    styles: [],
    imports: [MatDialogTitle, MatToolbar, MatIcon, CdkScrollable, MatDialogContent, MatDialogActions, MatButton, MatDialogClose]
})
export class ConfirmDialogComponent {
    constructor(
        @Inject(MatDialogRef) public readonly dialogRef: MatDialogRef<ConfirmDialogComponent>,
        @Inject(MAT_DIALOG_DATA) public readonly data: ConfirmDialogData
    ) {}

    onNoClick(): void {
        this.dialogRef.close();
    }
}
