import { SubmitterCreator } from '../../bases';
import { Component, Input } from '@angular/core';
import { MenuItem, SelectItem } from 'primeng/api';

import { HasAttributeList } from '../../interfaces';
import { ApiObject, ApiSubmitter, ApiAttribute, LinkDialogData, LinkItem } from '../../models';
import { SubmitterService, NewSubmitterLinkService } from '../../services';
import { UrlBuilder, NewSubmitterHelper, ApiComparators, LinkHelper } from '../../utils';
import { LinkDialogComponent } from '../link-dialog';
import { NewSubmitterDialogComponent } from '../new-submitter-dialog';

@Component({
  selector: 'app-submitters',
  templateUrl: './submitters.component.html',
  styleUrls: ['./submitters.component.css']
})
export class SubmittersComponent extends SubmitterCreator {
//  @Input() parentObject: ApiObject;
  @Input() parent: HasAttributeList;
  @Input() dataset: string;

  displaySubmitterDialog = false;
  displayLinkSubmitterDialog = false;
  displayUnlinkSubmitterDialog = false;

  submittermenuitems: MenuItem[] = [
    {
      label: 'Add submitter',
      icon: 'fa-plus-circle',
      command: (event: Event) => this.displaySubmitterDialog = true
    },
    {
      label: 'Link submitter',
      icon: 'fa-link',
      command: (event: Event) => this.displayLinkSubmitterDialog = true
    },
    {
      label: 'Unlink submitter',
      icon: 'fa-unlink',
      command: (event: Event) => this.displayUnlinkSubmitterDialog = true
    },
  ];

  constructor(
    public submitterService: SubmitterService,
    public newSubmitterLinkService: NewSubmitterLinkService,
  ) {
    super(newSubmitterLinkService);
  }

  submitterUB(): UrlBuilder {
    return new UrlBuilder(this.dataset, 'submitters');
  }

  submitterAnchor(): string {
    return undefined;
  }

  closeSubmitterDialog(): void {
    this.displaySubmitterDialog = false;
  }

  refreshSubmitter(submitter: ApiSubmitter): void {
    const attribute: ApiAttribute = {
      type: 'submitterlink',
      string: submitter.string,
      tail: '',
      attributes: new Array<ApiAttribute>()
    };
    this.parent.attributes.push(attribute);
    this.parent.save();
  }

  onSubmitterDialogClose() {
    this.displaySubmitterDialog = false;
  }

  onSubmitterDialogOpen(data: NewSubmitterDialogComponent) {
    if (data !== undefined) {
      const nsh = new NewSubmitterHelper();
      data._data = nsh.initNew('New Submitter');
    }
  }

  onLinkSubmitterDialogClose() {
    this.displayLinkSubmitterDialog = false;
  }

  onLinkSubmitterDialogOpen(dialog: LinkDialogComponent) {
    this.submitterService.getAll(dialog.dataset).subscribe(
      (value: ApiSubmitter[]) => this.lh().fillLinkData(dialog, value)
    );
  }

  linkSubmitter(data: LinkDialogData) {
    this.lh().link(data, this.parent.attributes, () => this.parent.save());
  }

  onUnlinkSubmitterDialogClose() {
    this.displayUnlinkSubmitterDialog = false;
  }

  onUnlinkSubmitterDialogOpen(dialog: LinkDialogComponent) {
    this.submitterService.getAll(dialog.dataset).subscribe(
      (value: ApiSubmitter[]) =>
        this.lh().fillUnlinkData(dialog, value, this.parent.attributes)
    );
  }

  unlinkSubmitter(data: LinkDialogData) {
    this.lh().unlink(data, this.parent.attributes, () => this.parent.save());
  }

  lh(): LinkHelper {
    const comparators: ApiComparators = new ApiComparators();
    return new LinkHelper((o: ApiSubmitter) => o.name, comparators.compareSubmitters, 'submitterlink');
  }
}
