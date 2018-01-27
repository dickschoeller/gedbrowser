import { Component, OnInit, Input } from '@angular/core';
import { ApiAttribute } from '../models';
import { StringUtil, NameUtil } from '../util';

@Component({
  selector: 'app-attribute-list-item',
  templateUrl: './attribute-list-item.component.html',
  styleUrls: ['./attribute-list-item.component.css']
})
export class AttributeListItemComponent implements OnInit {
  @Input() attribute: ApiAttribute;

  constructor() { }

  ngOnInit() {
  }

  label() {
    if (this.attribute.type === 'attribute') {
      return this.attribute.string;
    }
    return new StringUtil().titleCase(this.attribute.type);
  }

  contents() {
    if (this.attribute.type === 'attribute') {
      return this.attribute.tail;
    }
    if (this.attribute.type === 'name') {
      return new NameUtil().cleanup(this.attribute.string);
    }
    return this.attribute.string;
  }

  editable() {
    if (this.label() === 'Reference Number' || this.label() === 'Changed') {
      return false;
    }
    return true;
  }
}
