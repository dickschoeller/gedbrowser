import { Component, Inject, Input, OnChanges, OnInit, ViewChild } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { MatListOption, MatSelectionList } from '@angular/material/list';

import { ApiPerson, LinkPersonItem, LinkPersonDialogData } from '../../models';
import { SelectionModel } from '@angular/cdk/collections';

@Component({
  standalone: false,
  selector: 'app-link-person-dialog',
  template: `<div mat-dialog-title>
  <mat-toolbar color="primary"><mat-icon matListIcon>person</mat-icon>&nbsp; {{ data.titleString }}</mat-toolbar>
</div>
<div mat-dialog-content>
  <mat-selection-list #items (selectionChange)="onSelection($event, items.selectedOptions.selected)">
    <mat-list-option *ngFor="let item of data.items" [value]="item">{{ item.label }}</mat-list-option>
  </mat-selection-list>
</div>
<div mat-dialog-actions>
  <span class="example-fill-remaining-space"></span>
  <button mat-button [mat-dialog-close]="data" cdkFocusInitial>OK</button>
  <button mat-button (click)="onNoClick()" >Cancel</button>
</div>`,
    styles: []
})
export class LinkPersonDialogComponent
  implements OnInit, OnChanges {
  @Input() titleString: string;
  persons: Array<ApiPerson>;
  @ViewChild(MatSelectionList, {static: true}) selectionList: MatSelectionList;

  constructor(private route: ActivatedRoute,
    public dialogRef: MatDialogRef<LinkPersonDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: LinkPersonDialogData) {
  }

  ngOnInit() {
    this.selectionList.selectedOptions = new SelectionModel<MatListOption>(this.data.multi);
  }

  ngOnChanges() {
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
