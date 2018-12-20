import { MatDialog, MatDialogRef, } from '@angular/material';

import { RefreshSubmitter } from '../interfaces';
import { ApiSubmitter, NewSubmitterDialogData } from '../models';
import { NewSubmitterHelper, UrlBuilder } from '../utils';
import { NewSubmitterDialogComponent } from '../components/new-submitter-dialog';
import { SubmitterService } from '../services';

export abstract class SubmitterCreator implements RefreshSubmitter {
  data: NewSubmitterDialogData;

  constructor(
    public submitterService: SubmitterService,
    public dialog: MatDialog,
  ) {}

  createSubmitter(data: NewSubmitterDialogData): void {
    if (data != null && data !== undefined) {
      const newSubmitter: ApiSubmitter = NewSubmitterHelper.buildSubmitter(data);
      this.submitterService.postLink(this.submitterUB(), this.submitterAnchor(), newSubmitter)
        .subscribe((submitter: ApiSubmitter) => this.refreshSubmitter(submitter));
    }
  }

  openCreateSubmitterDialog() {
    const dialogRef = this.dialog.open(
      NewSubmitterDialogComponent,
      {
        data: { name: 'New Submitter' }
      });

    dialogRef.afterClosed().subscribe(result => {
      if (result !== undefined) {
        this.data = result;
        this.createSubmitter(this.data);
      }
    });
  }

  abstract submitterUB(): UrlBuilder;

  abstract submitterAnchor(): string;

  abstract refreshSubmitter(submitter: ApiSubmitter): void;

}
