import { Component, Inject, Input, OnInit, ViewChild } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { MatDialogRef, MAT_DIALOG_DATA, MatDialogTitle, MatDialogContent, MatDialogActions, MatDialogClose } from '@angular/material/dialog';
import { MatListOption, MatSelectionList } from '@angular/material/list';

import { ApiPerson, LinkPersonItem, LinkPersonDialogData } from '../../models';
import { SelectionModel } from '@angular/cdk/collections';
import { MatToolbar } from '@angular/material/toolbar';
import { MatIcon } from '@angular/material/icon';
import { CdkScrollable } from '@angular/cdk/scrolling';
import { MatButton } from '@angular/material/button';

@Component({
    selector: 'app-link-person-dialog',
    template: `<div mat-dialog-title>
  <mat-toolbar color="primary"><mat-icon matListIcon>person</mat-icon>&nbsp; {{ data.titleString }}</mat-toolbar>
</div>
<div mat-dialog-content>
  <mat-selection-list #items (selectionChange)="onSelection($event, items.selectedOptions.selected)">
    @for (item of data.items; track $index) {
      <mat-list-option [value]="item">{{ item.label }}</mat-list-option>
    }
  </mat-selection-list>
</div>
<div mat-dialog-actions>
  <span class="example-fill-remaining-space"></span>
  <button mat-button [mat-dialog-close]="data" cdkFocusInitial>OK</button>
  <button mat-button (click)="onNoClick()" >Cancel</button>
</div>`,
    styles: [],
    imports: [MatDialogTitle, MatToolbar, MatIcon, CdkScrollable, MatDialogContent, MatSelectionList, MatListOption, MatDialogActions, MatButton, MatDialogClose]
})
export class LinkPersonDialogComponent
  implements OnInit {
  @Input() titleString: string;
  persons: Array<ApiPerson>;
  @ViewChild(MatSelectionList, {static: true}) selectionList: MatSelectionList;

  constructor(@Inject(ActivatedRoute) @Inject(ActivatedRoute) @Inject(ActivatedRoute) private route: ActivatedRoute,
    @Inject(MatDialogRef<LinkPersonDialogComponent>) @Inject(MatDialogRef<LinkPersonDialogComponent>) @Inject(MatDialogRef<LinkPersonDialogComponent>) public dialogRef: MatDialogRef<LinkPersonDialogComponent>,
    @Inject(MAT_DIALOG_DATA) @Inject(LinkPersonDialogData) @Inject(LinkPersonDialogData) @Inject(LinkPersonDialogData) public data: LinkPersonDialogData) {
  }

  ngOnInit() {
    this.selectionList.selectedOptions = new SelectionModel<MatListOption>(this.data.multi);
  }

  onNoClick(): void {
    this.dialogRef.close();
  }

  onSelection(e, v: Array<MatListOption>) {
    this.data.selected = new Array<LinkPersonItem>();
    for (const a of v) {
      const li = { id: a.value.id, label: a.value.label, person: a.value.person };
      this.data.selected.push(li);
    }
    if (this.data.selected.length !== 0) {
      this.data.selectOne = this.data.selected[0];
    }
  }
}
