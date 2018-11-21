import { Component, Input } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { MatDialog } from '@angular/material';

import { SourceCreator } from '../../bases';
import { NewSourceDialogComponent } from '../../components';
import { NewSourceDialogData } from '../../models';
import { RefreshSource } from '../../interfaces';
import { ApiSource } from '../../models';
import { SourceService, NewSourceLinkService } from '../../services';
import { NewSourceHelper, UrlBuilder } from '../../utils';
import { SourceListPageComponent } from './source-list-page.component';

@Component({
  selector: 'app-source-list',
  templateUrl: './source-list.component.html',
  styleUrls: ['./source-list.component.css']
})
export class SourceListComponent extends SourceCreator implements RefreshSource {
  @Input() parent: RefreshSource;
  @Input() dataset: string;
  @Input() sources: Array<ApiSource>;

  constructor(public newSourceLinkService: NewSourceLinkService,
    public dialog: MatDialog,
  ) {
    super(newSourceLinkService, dialog);
  }

  sourceUB(): UrlBuilder {
    return new UrlBuilder(this.dataset, 'sources');
  }

  sourceAnchor(): string {
    return undefined;
  }

  refreshSource(source: ApiSource) {
    this.parent.refreshSource(source);
  }
}
