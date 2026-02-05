import {Component, Input, Inject } from '@angular/core';

import {ApiAttribute} from '../../models';
import { AttributeListItemDetailListItemComponent } from './attribute-list-item-detail-list-item.component';

@Component({
    selector: 'app-attribute-list-item-detail-list',
    template: `@for (attribute of attributes; track $index; let i = $index) {
  <app-attribute-list-item-detail-list-item
    [dataset]="dataset"
    [attribute]="attribute"
    [index]="i"
    [length]="attributes?.length">
  </app-attribute-list-item-detail-list-item>
}`,
    styles: [],
    imports: [AttributeListItemDetailListItemComponent]
})
export class AttributeListItemDetailListComponent {
  @Input() attributes: Array<ApiAttribute>;
  @Input() dataset: string;

  constructor() { }
}
