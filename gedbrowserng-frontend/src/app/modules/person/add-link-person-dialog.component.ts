import { Component, Inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import {
  MAT_DIALOG_DATA,
  MatDialogActions,
  MatDialogContent,
  MatDialogRef,
  MatDialogTitle
} from '@angular/material/dialog';
import { MatToolbar } from '@angular/material/toolbar';
import { MatIcon } from '@angular/material/icon';
import { MatTab, MatTabGroup } from '@angular/material/tabs';
import { MatFormField, MatLabel } from '@angular/material/form-field';
import { MatInput } from '@angular/material/input';
import { MatSelect, MatOption } from '@angular/material/select';
import { MatButton } from '@angular/material/button';

import { NewPersonDialogData } from '../../models';

export interface AddLinkPersonDialogData {
  title: string;
  defaultNewPerson: NewPersonDialogData;
}

export interface AddLinkPersonDialogResult {
  mode: 'new' | 'existing';
  newPersonData?: NewPersonDialogData;
  existingPersonId?: string;
}

@Component({
  selector: 'app-add-link-person-dialog',
  standalone: true,
  template: `<div mat-dialog-title>
  <mat-toolbar color="primary"><mat-icon matListIcon>person_add</mat-icon>&nbsp; {{ data.title }}</mat-toolbar>
</div>
<div mat-dialog-content class="dialog-content">
  <mat-tab-group [(selectedIndex)]="selectedTabIndex">
    <mat-tab label="Create and link">
      <div class="tab-content">
        <mat-form-field class="wide-field">
          <mat-label>Name</mat-label>
          <input matInput [(ngModel)]="newPersonData.name">
        </mat-form-field>
        <br/>
        <mat-form-field>
          <mat-label>Sex</mat-label>
          <mat-select [(ngModel)]="newPersonData.sex">
            <mat-option value="">-- Unknown --</mat-option>
            <mat-option value="M">Male</mat-option>
            <mat-option value="F">Female</mat-option>
          </mat-select>
        </mat-form-field>
        <br/>
        <mat-form-field class="wide-field">
          <mat-label>Birth date</mat-label>
          <input matInput [(ngModel)]="newPersonData.birthDate">
        </mat-form-field>
        <br/>
        <mat-form-field class="wide-field">
          <mat-label>Birth place</mat-label>
          <input matInput [(ngModel)]="newPersonData.birthPlace">
        </mat-form-field>
        <br/>
        <mat-form-field class="wide-field">
          <mat-label>Death date</mat-label>
          <input matInput [(ngModel)]="newPersonData.deathDate">
        </mat-form-field>
        <br/>
        <mat-form-field class="wide-field">
          <mat-label>Death place</mat-label>
          <input matInput [(ngModel)]="newPersonData.deathPlace">
        </mat-form-field>
      </div>
    </mat-tab>
    <mat-tab label="Link by ID">
      <div class="tab-content">
        <mat-form-field class="wide-field">
          <mat-label>Person ID</mat-label>
          <input matInput [(ngModel)]="existingPersonId" placeholder="Example: I1234">
        </mat-form-field>
      </div>
    </mat-tab>
  </mat-tab-group>
</div>
<div mat-dialog-actions>
  <span class="example-fill-remaining-space"></span>
  <button mat-button (click)="submit()" [disabled]="isSubmitDisabled()" cdkFocusInitial>OK</button>
  <button mat-button (click)="onNoClick()" >Cancel</button>
</div>`,
  styles: [`
.tab-content {
  padding-top: 12px;
  min-height: 20rem;
}

.wide-field {
  width: 100%;
}

.dialog-content {
  width: 100%;
}
`],
  imports: [
    MatDialogTitle,
    MatToolbar,
    MatIcon,
    MatDialogContent,
    MatTabGroup,
    MatTab,
    MatFormField,
    MatLabel,
    MatInput,
    FormsModule,
    MatSelect,
    MatOption,
    MatDialogActions,
    MatButton
  ]
})
export class AddLinkPersonDialogComponent {
  selectedTabIndex = 0;
  newPersonData: NewPersonDialogData;
  existingPersonId = '';

  constructor(
    @Inject(MatDialogRef) public readonly dialogRef: MatDialogRef<AddLinkPersonDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public readonly data: AddLinkPersonDialogData
  ) {
    this.newPersonData = { ...data.defaultNewPerson };
  }

  onNoClick(): void {
    this.dialogRef.close();
  }

  submit(): void {
    if (this.selectedTabIndex === 0) {
      this.dialogRef.close({
        mode: 'new',
        newPersonData: this.newPersonData
      } as AddLinkPersonDialogResult);
      return;
    }

    this.dialogRef.close({
      mode: 'existing',
      existingPersonId: this.existingPersonId.trim()
    } as AddLinkPersonDialogResult);
  }

  isSubmitDisabled(): boolean {
    if (this.selectedTabIndex !== 1) {
      return false;
    }
    return !this.existingPersonId.trim();
  }
}