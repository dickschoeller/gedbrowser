import {Component, OnInit, Input} from '@angular/core';

import {ApiAttribute} from '../../models';
import {ImageUtil} from '../../utils';

@Component({
  selector: 'app-attribute-list-item-detail-list-item',
  templateUrl: 'attribute-list-item-detail-list-item.component.html',
  styleUrls: ['./attribute-list-item-detail-list-item.component.css']
})
export class AttributeListItemDetailListItemComponent implements OnInit {
  @Input() attribute: ApiAttribute;
  @Input() index: number;
  @Input() length: number;
  @Input() dataset: string;

  constructor() { }

  ngOnInit() {
  }

  last() {
    return (this.index >= this.length - 1);
  }

  href() {
    if (this.attribute.type === 'sourcelink') {
      return '#/' + this.dataset + '/sources/' + this.attribute.string;
    }
    if (this.attribute.string === 'File') {
      return this.attribute.tail;
    }
    return null;
  }

  image(): boolean {
    return new ImageUtil().isImage(this.attribute);
  }

  displayString(): string {
    if (this.linkString()) {
      return '[' + this.attribute.string + ']';
    }
    if (this.stringOnly()) {
      return this.attribute.string;
    }
    if (this.tailOnly()) {
      return this.attribute.tail;
    }
    if (this.attribute.tail === '') {
      return this.attribute.type + ' ' + this.attribute.string;
    }
    return this.attribute.string + ' ' + this.attribute.tail;
  }

  linkString(): boolean {
    return (this.attribute.type === 'sourcelink' || this.attribute.type === 'submitterlink');
  }

  stringOnly(): boolean {
    return this.attribute.type === 'date' || this.attribute.type === 'place';
  }

  tailOnly(): boolean {
    return this.attribute.string === 'File' || this.attribute.string === 'Note';
  }
}
