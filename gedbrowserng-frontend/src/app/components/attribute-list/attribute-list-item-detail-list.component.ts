import {Component, OnInit, Input} from '@angular/core';

import {ApiAttribute} from '../../models';

@Component({
  standalone: false,
  selector: 'app-attribute-list-item-detail-list',
  template: `<app-attribute-list-item-detail-list-item
  *ngFor="let attribute of attributes; let i = index"
  [dataset]="dataset"
  [attribute]="attribute"
  [index]="i"
  [length]="attributes?.length">
</app-attribute-list-item-detail-list-item>`,
    styles: []
})
export class AttributeListItemDetailListComponent implements OnInit {
  @Input() attributes: Array<ApiAttribute>;
  @Input() dataset: string;

  constructor() { }

  ngOnInit() {
  }
}
