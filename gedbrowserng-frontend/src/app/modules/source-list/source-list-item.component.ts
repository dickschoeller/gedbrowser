import {Component, OnInit, Input} from '@angular/core';

import {ApiSource, ApiObject} from '../../models';
import {SourceService} from '../../services';

@Component({
  selector: 'app-source-list-item',
  templateUrl: './source-list-item.component.html',
  styleUrls: ['./source-list-item.component.css']
})
export class SourceListItemComponent implements OnInit {
  @Input() dataset: string;
  @Input() source: ApiSource;

  constructor(private sourceService: SourceService) {
  }

  ngOnInit() {
  }
}
