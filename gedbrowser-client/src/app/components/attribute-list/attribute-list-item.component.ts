import {Component, Input} from '@angular/core';
import {MatDialogRef, MatDialog} from '@angular/material';

import {ApiAttribute} from '../../models';
import {StringUtil, NameUtil, AttributeUtil} from '../../utils';

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
      width: '500px',
      height: '600px',
      data: this.attributeDialogHelper.buildData(false)
    };
    const dialogRef: MatDialogRef<AttributeDialogComponent> =
      this.dialog.open(AttributeDialogComponent, config);

    dialogRef.afterClosed().subscribe(result => {
      if (result === null || result === undefined) {
        return;
      }
      const data = result;
      this.attributeDialogHelper.populateParentAttribute(data);
      this.parent.save();
    });
  }

  moveUp(): void {
    const index = this.attributes.indexOf(this.attribute);
    this.attributes.splice(index - 1, 0, this.attributes.splice(index, 1)[0]);
    this.parent.save();
  }

  moveDown(): void {
    const index = this.attributes.indexOf(this.attribute);
    this.attributes.splice(index + 1, 0, this.attributes.splice(index, 1)[0]);
    this.parent.save();
  }

  delete(): void {
    const index = this.attributes.indexOf(this.attribute);
    this.attributes.splice(index, 1);
    this.parent.save();
  }
}
