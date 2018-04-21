import {Component, OnInit, Input} from '@angular/core';
import {MatDialog, MatDialogConfig, MatDialogRef} from '@angular/material';

import {ApiAttribute} from '../../models';
import {AttributeUtil} from '../../utils';

import {AttributeDialogData} from '../attribute-dialog/attribute-dialog-data';
import {AttributeDialogHelper} from '../attribute-dialog/attribute-dialog-helper';
import {AttributeDialogComponent} from '../attribute-dialog/attribute-dialog.component';

@Component({
  selector: 'app-attribute-list',
  templateUrl: './attribute-list.component.html',
  styleUrls: ['./attribute-list.component.css']
})
export class AttributeListComponent implements OnInit {
  @Input() attributes: Array<ApiAttribute>;
  @Input() parent: any;
  @Input() toggleable = false;
  @Input() styleClass: string;

  index;
  attributeDialogHelper = new AttributeDialogHelper(this);
  attributeUtil = new AttributeUtil(this);

  constructor(public dialog: MatDialog) { }

  ngOnInit() {
    this.index = this.attributeUtil.lastIndex();
  }

  createAttribute(): void {
    const dataIn: AttributeDialogData = {
      insert: true, index: this.index, type: 'Birth', text: '', date: '',
      place: '', note: '', originalType: 'Birth', originalText: '',
      originalDate: '', originalPlace: '', originalNote: '',
    };
    const dialogRef: MatDialogRef<AttributeDialogComponent> =
      this.dialog.open(AttributeDialogComponent, {
        data: dataIn,
      });

    const sub = dialogRef.componentInstance.onOK.subscribe(
      (data: AttributeDialogData) => {
        const attribute: ApiAttribute =
          this.attributeDialogHelper.populateNewAttribute(data);
        this.attributes.splice(0, 0, attribute);
        this.parent.save();
      }
    );

    dialogRef.afterClosed().subscribe(() => {
      sub.unsubscribe();
    });
  }
}
