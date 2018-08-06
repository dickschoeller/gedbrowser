import { RefreshSource } from '../interfaces';
import { ApiSource, NewSourceDialogData } from '../models';
import { NewSourceHelper, UrlBuilder } from '../utils';
import { NewSourceLinkService } from '../services';

export abstract class SourceCreator implements RefreshSource {
  nsh = new NewSourceHelper();

  constructor(public newSourceLinkService: NewSourceLinkService) {}

  createSource(data: NewSourceDialogData): void {
    if (data != null && data !== undefined) {
      const newSource: ApiSource = this.nsh.buildSource(data);
      this.newSourceLinkService.post(this.sourceUB(), this.sourceAnchor(), newSource)
        .subscribe((source: ApiSource) => this.refreshSource(source));
    }
  }

  abstract closeSourceDialog(): void;

  abstract sourceUB(): UrlBuilder;

  abstract sourceAnchor(): string;

  abstract refreshSource(source: ApiSource): void;
}
