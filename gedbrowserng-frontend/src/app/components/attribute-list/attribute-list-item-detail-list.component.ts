import {Component, Input } from '@angular/core';

import {ApiAttribute} from '../../models';
import { AttributeListItemDetailListItemComponent } from './attribute-list-item-detail-list-item.component';

@Component({
    selector: 'app-attribute-list-item-detail-list',
    template: `@for (attribute of visibleAttributes(); track $index; let i = $index) {
  <app-attribute-list-item-detail-list-item
    [dataset]="dataset"
    [attribute]="attribute"
    [index]="i"
    [length]="visibleAttributes().length">
  </app-attribute-list-item-detail-list-item>
}`,
    styles: [],
    imports: [AttributeListItemDetailListItemComponent]
})
export class AttributeListItemDetailListComponent {
  @Input() attributes: Array<ApiAttribute>;
  @Input() dataset: string;

  constructor() { }

  visibleAttributes(): Array<ApiAttribute> {
    return (this.attributes || []).filter((attribute) => !this.isModernPlace(attribute));
  }

  private isModernPlace(attribute: ApiAttribute): boolean {
    const normalize = (value: string | undefined) => (value || '').toLowerCase().replace(/[^a-z]/g, '');
    return normalize(attribute?.type) === 'modernplace'
      || normalize(attribute?.string) === 'modernplace';
  }
}
