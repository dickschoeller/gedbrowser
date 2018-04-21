import {Component, Input} from '@angular/core';
import {MatDialogRef, MatDialog} from '@angular/material';

import {ApiAttribute} from '../../models';
import {StringUtil, NameUtil, AttributeUtil} from '../../utils';
import { AttributeDialogData } from '../attribute-dialog';

import {AttributeDialogHelper} from '../attribute-dialog/attribute-dialog-helper';
import {AttributeDialogComponent} from '../attribute-dialog/attribute-dialog.component';

@Component({
  selector: 'app-attribute-list-item',
  templateUrl: './attribute-list-item.component.html',
  styleUrls: ['./attribute-list-item.component.css']
})
export class AttributeListItemComponent {
  @Input() attribute: ApiAttribute;
  @Input() attributes: Array<ApiAttribute>;
  @Input() index: number;
  @Input() parent: any;

  attributeUtil = new AttributeUtil(this);
  attributeDialogHelper = new AttributeDialogHelper(this);

  constructor(public dialog: MatDialog) { }

  edit(): void {
    const config = {
      data: this.attributeDialogHelper.buildData(false)
    };
    const dialogRef: MatDialogRef<AttributeDialogComponent> =
      this.dialog.open(AttributeDialogComponent, config);

    const sub = dialogRef.componentInstance.onOK.subscribe(
      (data: AttributeDialogData) => {
        this.attributeDialogHelper.populateParentAttribute(data);
        this.parent.save();
      }
    );

    dialogRef.afterClosed().subscribe(() => { sub.unsubscribe(); });
  }

  delete(): void {
    const index = this.attributes.indexOf(this.attribute);
    this.attributes.splice(index, 1);
    this.parent.save();
  }
}
