import { Component, Inject, Input, OnChanges, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { MatDialogRef, MAT_DIALOG_DATA, MatDialogTitle, MatDialogContent, MatDialogActions, MatDialogClose } from '@angular/material/dialog';
import { MatListOption, MatSelectionList } from '@angular/material/list';

import { LinkDialogInterface } from '../../interfaces';
import { LinkDialogData, LinkItem } from '../../models';
import { MatToolbar } from '@angular/material/toolbar';
import { MatIcon } from '@angular/material/icon';
import { CdkScrollable } from '@angular/cdk/scrolling';
import { NgFor } from '@angular/common';
import { MatButton } from '@angular/material/button';

@Component({
    selector: 'app-link-dialog',
    template: `<div mat-dialog-title>
  <mat-toolbar color="primary"><mat-icon matListIcon>contact_mail</mat-icon>&nbsp; {{ data.name }}</mat-toolbar>
</div>
<div mat-dialog-content>
  <mat-selection-list #items (selectionChange)="onSelection($event, items.selectedOptions.selected)">
    <mat-list-option *ngFor="let item of data.items" [value]="item.id">{{ item.label }}</mat-list-option>
  </mat-selection-list>
</div>
<div mat-dialog-actions>
  <span class="example-fill-remaining-space"></span>
  <button mat-button [mat-dialog-close]="data" cdkFocusInitial>OK</button>
  <button mat-button (click)="onNoClick()" >Cancel</button>
</div>`,
    styles: [],
    imports: [MatDialogTitle, MatToolbar, MatIcon, CdkScrollable, MatDialogContent, MatSelectionList, NgFor, MatListOption, MatDialogActions, MatButton, MatDialogClose]
})
export class LinkDialogComponent
  implements OnInit, OnChanges, LinkDialogInterface {
  @Input() titleString: string;
  objects: Array<any>;

  constructor(@Inject(ActivatedRoute) @Inject(ActivatedRoute) @Inject(ActivatedRoute) private route: ActivatedRoute,
    @Inject(MatDialogRef<LinkDialogComponent>) @Inject(MatDialogRef<LinkDialogComponent>) @Inject(MatDialogRef<LinkDialogComponent>) public dialogRef: MatDialogRef<LinkDialogComponent>,
    @Inject(MAT_DIALOG_DATA) @Inject(LinkDialogData) @Inject(LinkDialogData) @Inject(LinkDialogData) public data: LinkDialogData) {
  }

  ngOnInit() {
  }

  ngOnChanges() {
  }

  onNoClick(): void {
    this.dialogRef.close();
  }

  onSelection(e, v: Array<MatListOption>) {
    this.data.selected = new Array<LinkItem>();
    for (const a of v) {
      const li = { index: 0, label: a.getLabel(), id: a.value };
      this.data.selected.push(li);
    }
  }
}
