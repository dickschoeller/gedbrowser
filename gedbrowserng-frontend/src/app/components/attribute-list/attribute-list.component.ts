import { Component, OnInit, Input, OnChanges } from '@angular/core';
import { MatDialog } from '@angular/material';

import { ApiAttribute, AttributeDialogData, SelectItem } from '../../models';
import { AttributeDialogHelper, AttributeAnalyzer } from '../../utils';
import { HasAttributeList } from '../../interfaces';

import { HasAttributeDialog } from './has-attribute-dialog';

@Component({
  selector: 'app-attribute-list',
  templateUrl: './attribute-list.component.html',
  styleUrls: ['./attribute-list.component.css']
})
export class AttributeListComponent extends HasAttributeDialog implements OnInit, OnChanges, HasAttributeList {
  @Input() attributes: Array<ApiAttribute>;
  @Input() parent: HasAttributeList;
  @Input() toggleable = false;
  @Input() styleClass: string;
  @Input() dataset: string;
  @Input() showAdd = true;
  @Input() showNotes = true;
  @Input() showSources = true;
  @Input() showSubmitters = true;

  index;
  attributeDialogHelper = new AttributeDialogHelper(this);
  attributeUtil = new AttributeAnalyzer(this);

  constructor(public dialog: MatDialog) {
    super(dialog);
  }

  ngOnInit() {
    this.index = this.attributeUtil.lastIndex();
  }

  ngOnChanges() {
    this.index = this.attributeUtil.lastIndex();
  }

  openCreateAttributeDialog() {
    this.openAttributeDialog(result => { this.createAttribute(result); });
  }

  defaultData(): AttributeDialogData {
    return this.parent.defaultData();
  }

  createAttribute(data: AttributeDialogData) {
    if (data != null) {
      const attribute: ApiAttribute =
        this.attributeDialogHelper.populateNewAttribute(data);
      this.attributes.splice(0, 0, attribute);
      this.parent.save();
    }
  }

  options(): Array<SelectItem> {
    return this.parent.options();
  }

  save() {
    this.parent.save();
  }
}
