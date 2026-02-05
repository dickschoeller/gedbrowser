import {Component, OnInit, Input, Inject } from '@angular/core';

import {ApiAttribute} from '../../models';
import { NgFor } from '@angular/common';
import { AttributeListItemDetailListItemComponent } from './attribute-list-item-detail-list-item.component';

@Component({
    selector: 'app-attribute-list-item-detail-list',
    template: `<app-attribute-list-item-detail-list-item
  *ngFor="let attribute of attributes; let i = index"
  [dataset]="dataset"
  [attribute]="attribute"
  [index]="i"
  [length]="attributes?.length">
</app-attribute-list-item-detail-list-item>`,
    styles: [],
    imports: [NgFor, AttributeListItemDetailListItemComponent]
})
export class AttributeListItemDetailListComponent implements OnInit {
  @Input() attributes: Array<ApiAttribute>;
  @Input() dataset: string;

  constructor() { }

  ngOnInit() {
  }
}
