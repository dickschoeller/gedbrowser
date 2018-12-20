import { MatDialog, MatDialogRef, } from '@angular/material';

import { RefreshSource } from '../interfaces';
import { ApiSource, NewSourceDialogData } from '../models';
import { NewSourceHelper, UrlBuilder } from '../utils';
import { NewSourceDialogComponent } from '../components/new-source-dialog';
import { SourceService } from '../services';

export abstract class SourceCreator implements RefreshSource {
  data: NewSourceDialogData;

  constructor(
    public sourceService: SourceService,
    public dialog: MatDialog,
  ) {}

  createSource(data: NewSourceDialogData): void {
    if (data != null && data !== undefined) {
      const newSource: ApiSource = NewSourceHelper.buildSource(data);
      this.sourceService.postLink(this.sourceUB(), this.sourceAnchor(), newSource)
        .subscribe((source: ApiSource) => this.refreshSource(source));
    }
  }

  openCreateSourceDialog(): void {
    const dialogRef = this.dialog.open(
      NewSourceDialogComponent,
      {
        data: { title: 'New Source', abbreviation: 'NewSource', text: '' }
      });

    dialogRef.afterClosed().subscribe(result => {
      if (result !== undefined) {
        this.data = result;
        this.createSource(this.data);
      }
    });
  }

  abstract sourceUB(): UrlBuilder;

  abstract sourceAnchor(): string;

  abstract refreshSource(source: ApiSource): void;
}
