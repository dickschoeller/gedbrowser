import {Component, OnInit, Input} from '@angular/core';

import {ApiSubmitter, ApiObject} from '../../models';
import {SubmitterService} from '../../services';

@Component({
  selector: 'app-submitter-list-item',
  templateUrl: './submitter-list-item.component.html',
  styleUrls: ['./submitter-list-item.component.css']
})
export class SubmitterListItemComponent implements OnInit {
  @Input() submitter: ApiSubmitter;

  constructor(private submitterService: SubmitterService) {
  }

  ngOnInit() {
  }
}
