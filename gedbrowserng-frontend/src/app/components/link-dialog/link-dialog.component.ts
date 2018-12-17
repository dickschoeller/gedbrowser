import { Component, Inject, Input, OnChanges, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';
import { MatListOption } from '@angular/material/list';

import { LinkDialogInterface } from '../../interfaces';
import { LinkDialogData, LinkItem } from '../../models';

@Component({
  selector: 'app-link-dialog',
  templateUrl: './link-dialog.component.html',
  styleUrls: ['./link-dialog.component.css']
})
export class LinkDialogComponent
  implements OnInit, OnChanges, LinkDialogInterface {
  @Input() titleString: string;
  dataset: string;
  objects: Array<any>;

  constructor(private route: ActivatedRoute,
    private router: Router,
    public dialogRef: MatDialogRef<LinkDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: LinkDialogData) {
  }

  ngOnInit() {
    this.init();
  }

  ngOnChanges() {
    this.init();
  }

  private init(): void {
    this.route.params.subscribe((params) => {
      this.dataset = params['dataset'];
    });
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
