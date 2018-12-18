import { Component, Inject, Input, OnChanges, OnInit, ViewChild } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';
import { MatListOption, MatSelectionList } from '@angular/material/list';

import { ApiPerson, LinkPersonItem, LinkPersonDialogData } from '../../models';
import { SelectionModel } from '@angular/cdk/collections';

@Component({
  selector: 'app-link-person-dialog',
  templateUrl: './link-person-dialog.component.html',
  styleUrls: ['./link-person-dialog.component.css']
})
export class LinkPersonDialogComponent
  implements OnInit, OnChanges {
  @Input() titleString: string;
  persons: Array<ApiPerson>;
  @ViewChild(MatSelectionList) selectionList: MatSelectionList;

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
