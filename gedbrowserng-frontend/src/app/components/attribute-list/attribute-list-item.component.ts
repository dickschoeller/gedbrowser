import {OnInit, Component, Input} from '@angular/core';
import {MenuItem, SelectItem} from 'primeng/api';

import {HasAttributeList} from '../../interfaces';
import {ApiAttribute} from '../../models';
import {AttributeUtil, NameUtil, StringUtil, UrlBuilder} from '../../utils';

import {AttributeDialogData, AttributeDialogHelper, NewAttributeDialogComponent} from '../attribute-dialog';

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
  @Input() dataset: string;

  displayAttributeDialog = false;
  attributeUtil = new AttributeUtil(this);
  attributeDialogHelper: AttributeDialogHelper = new AttributeDialogHelper(this);
  _data: AttributeDialogData;

  constructor() {}

  ngOnInit() {}

  edit() {
    this.displayAttributeDialog = true;
  }

  defaultData(): AttributeDialogData {
    const adh: AttributeDialogHelper = new AttributeDialogHelper(this);
    return adh.buildData(false);
  }

  onAttributeDialogOpen(data: NewAttributeDialogComponent) {
    data.data = this.defaultData;
  }

  onAttributeDialogOK(data: AttributeDialogData) {
    if (data != null) {
      this.attributeDialogHelper.populateParentAttribute(data);
      this.parent.save();
    }
  }

  onAttributeDialogClose() {
    this.displayAttributeDialog = false;
  }

  options(): Array<SelectItem> {
    return this.parent.options();
  }

  delete(): void {
    const index = this.attributes.indexOf(this.attribute);
    this.attributes.splice(index, 1);
    this.parent.save();
  }

  href() {
    if (this.attribute.type === 'sourcelink') {
      return '#/' + this.dataset + '/sources/' + this.attribute.string;
    }
    if (this.attribute.type === 'submitterlink') {
      return '#/' + this.dataset + '/submitters/' + this.attribute.string;
    }
    if (this.attribute.type === 'submissionlink') {
      return '#/' + this.dataset + '/submissions/' + this.attribute.string;
    }
    if (this.attribute.string === 'File') {
      return this.attribute.tail;
    }
    return null;
  }
}
