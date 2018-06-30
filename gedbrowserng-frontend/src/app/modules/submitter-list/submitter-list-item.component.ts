import { Component, Input } from '@angular/core';

import { RefreshSubmitter } from '../../interfaces';
import { ApiSubmitter, ApiObject } from '../../models';
import { SubmitterService } from '../../services';

@Component({
  selector: 'app-submitter-list-item',
  templateUrl: './submitter-list-item.component.html',
  styleUrls: ['./submitter-list-item.component.css']
})
export class SubmitterListItemComponent {
  @Input() parent: RefreshSubmitter;
  @Input() dataset: string;
  @Input() submitter: ApiSubmitter;

  constructor(private submitterService: SubmitterService) {
  }

  delete() {
    this.submitterService.delete(this.dataset, this.submitter).subscribe((submitter: ApiSubmitter) => {
      this.parent.refreshSubmitter(submitter);
    });
  }
}
