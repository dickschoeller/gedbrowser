import {Component, Input} from '@angular/core';

import {ApiSubmitter} from '../../models';
import {SubmitterService} from '../../services';
import {SubmitterListPageComponent} from './submitter-list-page.component';

@Component({
  selector: 'app-submitter-list',
  templateUrl: './submitter-list.component.html',
  styleUrls: ['./submitter-list.component.css']
})
export class SubmitterListComponent {
  @Input() p: SubmitterListPageComponent;
  @Input() dataset: string;
  @Input() submitters: ApiSubmitter[];
  display = false;

  constructor() {}

  openCreateSubmitterDialog(): void {
    this.display = true;
  }

  closeDialog(): void {
    this.display = false;
  }
}
