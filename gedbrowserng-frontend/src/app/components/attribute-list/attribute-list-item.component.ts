import { Component, Input } from '@angular/core';
import { MatDialog } from '@angular/material';

import { HasAttributeList } from '../../interfaces';
import { ApiAttribute, AttributeDialogData, SelectItem } from '../../models';
import { AttributeDialogHelper, AttributeAnalyzer, NameUtil, UrlBuilder } from '../../utils';

import { NewAttributeDialogComponent } from '../attribute-dialog';

@Component({
  selector: 'app-attribute-list-item',
  templateUrl: './attribute-list-item.component.html',
  styleUrls: ['./attribute-list-item.component.css']
})
export class AttributeListItemComponent implements HasAttributeList {
  @Input() attribute: ApiAttribute;
  @Input() attributeList: Array<ApiAttribute>;
  @Input() index: number;
  @Input() parent: HasAttributeList;
  @Input() dataset: string;

  attributeUtil = new AttributeAnalyzer(this);
  attributeDialogHelper: AttributeDialogHelper = new AttributeDialogHelper(this);
  _data: AttributeDialogData;
  get attributes(): Array<ApiAttribute> {
    return this.attribute.attributes;
  }

  constructor(public dialog: MatDialog) { }

  edit() {
    const dialogRef = this.dialog.open(
      NewAttributeDialogComponent,
      {
        data: { options: this.options(), data: this.defaultData() }
      });

    dialogRef.afterClosed().subscribe(result => {
      if (result !== undefined) {
        this.modifyAttribute(result);
      }
    });
// <!-- app-new-attribute-dialog
//    [p]="this" [options]="options()" (emitClose)="onAttributeDialogClose()"
//    (emitOpen)="onAttributeDialogOpen($event)" (emitOK)="onAttributeDialogOK($event)"
//    [(display)]="displayAttributeDialog"></app-new-attribute-dialog -->

  }

  defaultData(): AttributeDialogData {
    const adh: AttributeDialogHelper = new AttributeDialogHelper(this);
    return adh.buildData(false);
  }

  modifyAttribute(data: AttributeDialogData) {
    this.attributeDialogHelper.populateParentAttribute(data);
    this.parent.save();
  }

  options(): Array<SelectItem> {
    return this.parent.options();
  }

  delete(): void {
    const index = this.attributeList.indexOf(this.attribute);
    this.attributeList.splice(index, 1);
    this.parent.save();
  }

  href() {
    return this.attributeUtil.href();
  }

  save() {
    this.parent.save();
  }
}
