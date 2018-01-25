import {Component, OnInit, Input} from '@angular/core';
import {SubmitterService, ApiSubmitter, ApiObject} from '../shared';

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
