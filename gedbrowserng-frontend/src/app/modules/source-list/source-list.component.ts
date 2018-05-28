import {Component, Input} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';

import {SourceCreator} from '../../bases';
import {NewSourceDialogComponent} from '../../components';
import {ApiSource, NewPersonDialogData} from '../../models';
import {SourceService, NewSourceLinkService} from '../../services';
import {NewSourceHelper, UrlBuilder} from '../../utils';
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
  displaySourceDialog = false;

  constructor(public newSourceLinkService: NewSourceLinkService) {
    super(newSourceLinkService);
  }

  sourceUB(): UrlBuilder {
    return new UrlBuilder(this.dataset, 'sources');
  }

  openCreateSourceDialog(): void {
    this.displaySourceDialog = true;
  }

  closeSourceDialog(): void {
    this.displaySourceDialog = false;
  }

  onDialogOpen(data: NewSourceDialogComponent) {
    data._data = this.nsh.initNew('New Source');
  }

  sourceAnchor(): string {
    return undefined;
  }

  refreshSource(source: ApiSource) {
    this.p.refreshSource();
  }
}
