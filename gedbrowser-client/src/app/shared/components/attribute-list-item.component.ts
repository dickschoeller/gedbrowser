import { Component, OnInit, Input } from '@angular/core';
import { ApiAttribute } from '../models';
import { StringUtil } from '../util';

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
    const su = new StringUtil();
    return su.titleCase(this.attribute.type);
  }

  contents() {
    if (this.attribute.type === 'attribute') {
      return this.attribute.tail;
    }
    if (this.attribute.type === 'name') {
      const su = new StringUtil();
      return su.replaceAll(su.replaceAll(this.attribute.string, '/', ' '), '  ', ' ').trim();
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
