import { Component, Input } from '@angular/core';

import { ApiAttribute } from '../../models';
import { AttributeAnalyzer, ImageUtil } from '../../utils';

@Component({
    selector: 'app-attribute-list-item-detail-list-item',
    template: `@if (href()) {
  <span>
    @if (!image()) {
      <a href="{{ href() }}">{{ displayString() }}</a>
    } @else {
      <div><a href="{{ displayString() }}"><img src="{{ displayString() }}" width="300px"/></a></div>
    }
  </span>
} @else {
  <span>{{ displayString() }}@if (!last()) { <span>,</span> }</span>
}`,
    styles: [],
    imports: []
})
export class AttributeListItemDetailListItemComponent {
  @Input() attribute: ApiAttribute;
  @Input() index: number;
  @Input() length: number;
  @Input() dataset: string;
  attributeUtil = new AttributeAnalyzer(this);

  constructor() { }

  last() {
    return (this.index >= this.length - 1);
  }

  href() {
    return this.attributeUtil.href();
  }

  image(): boolean {
    return ImageUtil.isImage(this.attribute);
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
    return (this.attribute.type === 'sourcelink'
      || this.attribute.type === 'submitterlink'
      || this.attribute.type === 'notelink');
  }

  stringOnly(): boolean {
    return this.attribute.type === 'date' || this.attribute.type === 'place';
  }

  tailOnly(): boolean {
    return this.attribute.string === 'File'
      || this.attribute.string === 'Note'
      || this.attribute.string === 'Title';
  }
}
