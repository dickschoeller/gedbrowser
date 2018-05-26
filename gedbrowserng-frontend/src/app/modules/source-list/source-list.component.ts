import {Component, Input} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';

import {SourceCreator} from '../../bases';
import {
  NewSourceDialogComponent,
  NewSourceDialogData,
  NewSourceHelper
} from '../../components';
import {ApiSource} from '../../models';
import {SourceService, NewSourceLinkService} from '../../services';
import {UrlBuilder} from '../../utils';
import {SourceListPageComponent} from './source-list-page.component';

@Component({
  selector: 'app-source-list',
  templateUrl: './source-list.component.html',
  styleUrls: ['./source-list.component.css']
})
export class SourceListComponent extends SourceCreator {
  @Input() p: SourceListPageComponent;
  @Input() dataset: string;
  @Input() sources: Array<ApiSource>;
  display = false;

  constructor(public newSourceLinkService: NewSourceLinkService) {
    super(newSourceLinkService);
  }

  ub(): UrlBuilder {
    return new UrlBuilder(this.dataset, 'sources');
  }

  openCreateSourceDialog(): void {
    this.display = true;
  }

  closeDialog(): void {
    this.display = false;
  }

  onDialogOpen(data: NewSourceDialogComponent) {
    data._data = this.nsh.initNew('New Source');
  }

  anchor(): string {
    return undefined;
  }

  refreshSource() {
    this.p.refreshSource();
  }
}
