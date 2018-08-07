import { RefreshSubmitter } from '../interfaces';
import { ApiSubmitter, NewSubmitterDialogData } from '../models';
import { NewSubmitterHelper, UrlBuilder } from '../utils';
import { NewSubmitterLinkService } from '../services';

export abstract class SubmitterCreator implements RefreshSubmitter {
  constructor(public newSubmitterLinkService: NewSubmitterLinkService) {}

  createSubmitter(data: NewSubmitterDialogData): void {
    if (data != null && data !== undefined) {
      const newSubmitter: ApiSubmitter = NewSubmitterHelper.buildSubmitter(data);
      this.newSubmitterLinkService.post(this.submitterUB(), this.submitterAnchor(), newSubmitter)
        .subscribe((submitter: ApiSubmitter) => this.refreshSubmitter(submitter));
    }
  }

  abstract closeSubmitterDialog(): void;

  abstract submitterUB(): UrlBuilder;

  abstract submitterAnchor(): string;

  abstract refreshSubmitter(submitter: ApiSubmitter): void;

}
