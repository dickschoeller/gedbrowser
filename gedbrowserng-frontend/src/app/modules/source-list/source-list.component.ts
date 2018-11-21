import { Component, Input } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { MatDialog, MatDialogRef, } from '@angular/material';

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

  data: NewSourceDialogData;

  constructor(public newSourceLinkService: NewSourceLinkService,
    public dialog: MatDialog,
  ) {
    super(newSourceLinkService);
  }

  sourceUB(): UrlBuilder {
    return new UrlBuilder(this.dataset, 'sources');
  }

  openCreateSourceDialog(): void {
    const dialogRef = this.dialog.open(
      NewSourceDialogComponent,
      {
        data: NewSourceHelper.initNew('New Source')
      });

    dialogRef.afterClosed().subscribe(result => {
      if (result !== undefined) {
        this.data = result;
        this.createSource(this.data);
      }
    });
  }

  sourceAnchor(): string {
    return undefined;
  }

  refreshSource(source: ApiSource) {
    this.parent.refreshSource(source);
  }
}
