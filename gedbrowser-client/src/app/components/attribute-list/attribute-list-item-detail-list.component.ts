import {Component, OnInit, Input} from '@angular/core';

import {ApiAttribute} from '../../models';

@Component({
  selector: 'app-attribute-list-item-detail-list',
  templateUrl: './attribute-list-item-detail-list.component.html',
  styleUrls: ['./attribute-list-item-detail-list.component.css']
})
export class AttributeListItemDetailListComponent implements OnInit {
  @Input() attributes: Array<ApiAttribute>;

  constructor() { }

  ngOnInit() {
  }
}
