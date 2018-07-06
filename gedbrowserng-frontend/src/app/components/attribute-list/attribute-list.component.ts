import { Component, OnInit, Input, OnChanges } from '@angular/core';
import { SelectItem } from 'primeng/api';

import { ApiAttribute } from '../../models';
import { AttributeUtil } from '../../utils';
import { HasAttributeList } from '../../interfaces';

import {
  AttributeDialogData,
  AttributeDialogHelper,
  NewAttributeDialogComponent
} from '../attribute-dialog';

@Component({
  selector: 'app-attribute-list',
  templateUrl: './attribute-list.component.html',
  styleUrls: ['./attribute-list.component.css']
})
export class AttributeListComponent implements OnInit, OnChanges, HasAttributeList {
  @Input() attributes: Array<ApiAttribute>;
  @Input() parent: HasAttributeList;
  @Input() toggleable = false;
  @Input() styleClass: string;
  @Input() dataset: string;

  display = false;
  index;
  attributeDialogHelper = new AttributeDialogHelper(this);
  attributeUtil = new AttributeUtil(this);

  constructor() { }

  ngOnInit() {
    this.index = this.attributeUtil.lastIndex();
  }

  ngOnChanges() {
    this.index = this.attributeUtil.lastIndex();
  }

  create() {
    this.display = true;
  }

  defaultData(): AttributeDialogData {
    return this.parent.defaultData();
  }

  onDialogOpen(data: NewAttributeDialogComponent) {
    data._data = this.defaultData();
  }

  onDialogOK(data: AttributeDialogData) {
    if (data != null) {
      const attribute: ApiAttribute =
        this.attributeDialogHelper.populateNewAttribute(data);
      this.attributes.splice(0, 0, attribute);
      this.parent.save();
    }
  }

  onDialogClose() {
    this.display = false;
  }

  options(): Array<SelectItem> {
    return this.parent.options();
  }

  save() {
    this.parent.save();
  }
}
