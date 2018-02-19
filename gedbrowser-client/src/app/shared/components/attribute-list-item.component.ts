import {Component, Input} from '@angular/core';
import {MatDialogRef, MatDialog} from '@angular/material';
import {ApiAttribute} from '../models';
import {StringUtil, NameUtil, AttributeUtil} from '../util';
import {AttributeDialogHelper} from './attribute-dialog-helper';
import {AttributeDialogComponent} from './attribute-dialog.component';

@Component({
  selector: 'app-attribute-list-item',
  templateUrl: './attribute-list-item.component.html',
  styleUrls: ['./attribute-list-item.component.css']
})
export class AttributeListItemComponent {
  @Input() attribute: ApiAttribute;
  @Input() attributes: Array<ApiAttribute>;
  attributeUtil = new AttributeUtil(this);
  attributeDialogHelper = new AttributeDialogHelper(this);

  constructor(public dialog: MatDialog) { }

  edit(): void {
    const config = {
      width: '500px',
      height: '600px',
      data: this.attributeDialogHelper.buildData()
    };
    const dialogRef: MatDialogRef<AttributeDialogComponent> =
      this.dialog.open(AttributeDialogComponent, config);

    dialogRef.afterClosed().subscribe(result => {
      if (result === null) {
        return;
      }
      const data = result;
      this.attributeDialogHelper.populateAttribute(data);
    });
  }

  moveUp(): void {
    const index = this.attributes.indexOf(this.attribute);
    this.attributes.splice(index - 1, 0, this.attributes.splice(index, 1)[0]);
  }

  moveDown(): void {
    const index = this.attributes.indexOf(this.attribute);
    this.attributes.splice(index + 1, 0, this.attributes.splice(index, 1)[0]);
  }

  delete(): void {
    const index = this.attributes.indexOf(this.attribute);
    this.attributes.splice(index, 1);
  }
}
