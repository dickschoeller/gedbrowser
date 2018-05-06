import {OnInit, Component, Input} from '@angular/core';
import {SelectItem} from 'primeng/api';

import {ApiAttribute} from '../../models';
import {StringUtil, NameUtil, AttributeUtil} from '../../utils';
import {HasAttributeList} from '../../interfaces';

import {
  AttributeDialogData,
  AttributeDialogHelper,
  NewAttributeDialogComponent
} from '../attribute-dialog';

@Component({
  selector: 'app-attribute-list-item',
  templateUrl: './attribute-list-item.component.html',
  styleUrls: ['./attribute-list-item.component.css']
})
export class AttributeListItemComponent implements OnInit {
  @Input() attribute: ApiAttribute;
  @Input() attributes: Array<ApiAttribute>;
  @Input() index: number;
  @Input() parent: HasAttributeList;

  display = false;
  attributeUtil = new AttributeUtil(this);
  attributeDialogHelper: AttributeDialogHelper = new AttributeDialogHelper(this);
  _data: AttributeDialogData;

  constructor() { }

  ngOnInit() {
  }

  edit() {
    this.display = true;
  }

  defaultData(): AttributeDialogData {
    const adh: AttributeDialogHelper = new AttributeDialogHelper(this);
    return adh.buildData(false);
  }

  onDialogOpen(data: NewAttributeDialogComponent) {
    data.data = this.defaultData;
  }

  onDialogOK(data: AttributeDialogData) {
    if (data != null) {
      this.attributeDialogHelper.populateParentAttribute(data);
      this.parent.save();
    }
  }

  onDialogClose() {
    this.display = false;
  }

  options(): Array<SelectItem> {
    return this.parent.options();
  }

  delete(): void {
    const index = this.attributes.indexOf(this.attribute);
    this.attributes.splice(index, 1);
    this.parent.save();
  }
}
