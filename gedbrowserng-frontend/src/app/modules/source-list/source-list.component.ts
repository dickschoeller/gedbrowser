import {Component, Input} from '@angular/core';

import {ApiSource} from '../../models';
import {SourceService} from '../../services';
import {SourceListPageComponent} from './source-list-page.component';

@Component({
  selector: 'app-source-list',
  templateUrl: './source-list.component.html',
  styleUrls: ['./source-list.component.css']
})
export class SourceListComponent {
  @Input() p: SourceListPageComponent;
  @Input() dataset: string;
  @Input() sources: Array<ApiSource>;
  display = false;

  constructor() {}

  openCreateSourceDialog(): void {
    this.display = true;
  }

  closeDialog(): void {
    this.display = false;
  }
}
