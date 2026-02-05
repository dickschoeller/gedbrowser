import { Component, Inject, Input, EventEmitter, OnInit, Output } from '@angular/core';
import { CdkDragDrop, moveItemInArray, CdkDropList, CdkDrag } from '@angular/cdk/drag-drop';

import { MatDialogRef, MAT_DIALOG_DATA, MatDialogTitle, MatDialogContent, MatDialogActions, MatDialogClose } from '@angular/material/dialog';

import { MultimediaDialogData, MultimediaFileData, MultimediaFormat, MultimediaSourceType } from '../../models';
import { SelectItem } from '../../models/select-item';
import { UserService } from '../../services';
import { MatToolbar } from '@angular/material/toolbar';
import { MatIcon } from '@angular/material/icon';
import { CdkScrollable } from '@angular/cdk/scrolling';
import { MatFormField, MatLabel } from '@angular/material/form-field';
import { MatInput } from '@angular/material/input';
import { FormsModule } from '@angular/forms';
import { NgFor } from '@angular/common';
import { MatSelect, MatOption } from '@angular/material/select';
import { MatButton } from '@angular/material/button';

@Component({
    selector: 'app-multimedia-dialog',
    template: `<div mat-dialog-title>
  <mat-toolbar color="primary"><mat-icon matListIcon>image</mat-icon> &nbsp; Multimedia item</mat-toolbar>
</div>
<div mat-dialog-content>
  <mat-form-field>
    <mat-label>Title</mat-label>
    <input matInput [(ngModel)]="data.title">
  </mat-form-field>
  <br/>
  <div>
    <div cdkDropList class="file-list" (cdkDropListDropped)="drop($event)"
        [cdkDropListDisabled]="!hasSignedIn()">
      <div cdkDrag class="{{ hasSignedIn() ? 'file-box' : '' }}"
          *ngFor="let file of data.files; let i=index">
        <br/>
        <mat-form-field>
          <mat-label>File URL</mat-label>
          <input matInput [(ngModel)]="file.fileUrl">
        </mat-form-field>
        <br/>
        <mat-form-field>
          <mat-label>Format</mat-label>
          <mat-select [(ngModel)]="file.format">
            <mat-option *ngFor="let format of formats" [value]="format.value">{{ format.label }}</mat-option>
          </mat-select>
        </mat-form-field>
        <br/>
        <mat-form-field>
          <mat-label>Format</mat-label>
          <mat-select [(ngModel)]="file.sourceType">
            <mat-option *ngFor="let type of sourceTypes" [value]="type.value">{{ type.label }}</mat-option>
          </mat-select>
        </mat-form-field>
      </div>
    </div>
  </div>
  <br/>
  <mat-form-field>
    <mat-label>Note</mat-label>
    <textarea matInput [rows]="8" [cols]="100" [(ngModel)]="data.note"
        autoresize="autoResize"></textarea>
  </mat-form-field>
</div>
<div mat-dialog-actions>
  <span class="example-fill-remaining-space"></span>
  <button mat-button [mat-dialog-close]="data" cdkFocusInitial>OK</button>
  <button mat-button (click)="onNoClick()" >Cancel</button>
</div>`,
    styles: [],
    imports: [MatDialogTitle, MatToolbar, MatIcon, CdkScrollable, MatDialogContent, MatFormField, MatLabel, MatInput, FormsModule, CdkDropList, NgFor, CdkDrag, MatSelect, MatOption, MatDialogActions, MatButton, MatDialogClose]
})
export class MultimediaDialogComponent implements OnInit {
    formats: Array<SelectItem>;
    sourceTypes: Array<SelectItem>;

    constructor(
        @Inject(MatDialogRef) public dialogRef: MatDialogRef<MultimediaDialogComponent>,
        @Inject(UserService) private userService: UserService,
        @Inject(MAT_DIALOG_DATA) public data: MultimediaDialogData) {
    }

    ngOnInit() {
        this.formats = this.initFormats();
        this.sourceTypes = this.initSourceTypes();
    }

    onNoClick(): void {
        this.dialogRef.close();
    }

    initFormats(): Array<SelectItem> {
        const formats: Array<SelectItem> = new Array<SelectItem>();
        for (const formatString of Object.keys(MultimediaFormat)) {
            formats.push({ label: formatString, value: MultimediaFormat[formatString] });
        }
        return formats;
    }

    initSourceTypes(): Array<SelectItem> {
        const sourceTypes: Array<SelectItem> = new Array<SelectItem>();
        for (const sourceTypeString of Object.keys(MultimediaSourceType)) {
            sourceTypes.push({ label: sourceTypeString, value: MultimediaSourceType[sourceTypeString] });
        }
        return sourceTypes;
    }

    drop(event: CdkDragDrop<string[]>) {
        moveItemInArray(this.data.files, event.previousIndex, event.currentIndex);
    }

    hasSignedIn() {
        return !!this.userService.currentUser;
    }
}
