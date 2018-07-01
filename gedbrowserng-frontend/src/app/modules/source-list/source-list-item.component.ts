import { Component, Input } from '@angular/core';

import { RefreshSource } from '../../interfaces';
import { ApiSource, ApiObject } from '../../models';
import { SourceService } from '../../services';

@Component({
  selector: 'app-source-list-item',
  templateUrl: './source-list-item.component.html',
  styleUrls: ['./source-list-item.component.css']
})
export class SourceListItemComponent {
  @Input() parent: RefreshSource;
  @Input() dataset: string;
  @Input() source: ApiSource;

  constructor(private sourceService: SourceService) {
  }

  delete() {
    this.sourceService.delete(this.dataset, this.source).subscribe((source: ApiSource) => {
      this.parent.refreshSource(source);
    });
  }
}
