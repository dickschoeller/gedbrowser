import { SubmitterCreator } from '../../bases';
import { Component, Input } from '@angular/core';
import { MenuItem, SelectItem } from 'primeng/api';

import { HasAttributeList } from '../../interfaces';
import { ApiObject, ApiSubmitter, ApiAttribute, LinkDialogData, LinkItem } from '../../models';
import { SubmitterService, NewSubmitterLinkService } from '../../services';
import { UrlBuilder, NewSubmitterHelper, ApiComparators } from '../../utils';
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
      command: (event: Event) => { this.openCreateSubmitterDialog(); }
    },
    {
      label: 'Link submitter',
      icon: 'fa-link',
      command: (event: Event) => { this.openLinkSubmitterDialog(); }
    },
    {
      label: 'Unlink submitter',
      icon: 'fa-unlink',
      command: (event: Event) => { this.openUnlinkSubmitterDialog(); }
    },
  ];

  constructor(
    public submitterService: SubmitterService,
    public newSubmitterLinkService: NewSubmitterLinkService,
  ) {
    super(newSubmitterLinkService);
  }

  submitterUB(): UrlBuilder {
    // This would enable creating a submitter but not linking.
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

  openCreateSubmitterDialog() {
    this.displaySubmitterDialog = true;
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

  openLinkSubmitterDialog() {
    this.displayLinkSubmitterDialog = true;
  }

  onLinkSubmitterDialogClose() {
    this.displayLinkSubmitterDialog = false;
  }

  onLinkSubmitterDialogOpen(dialog: LinkDialogComponent) {
    this.submitterService.getAll(dialog.dataset).subscribe(
      (value: ApiSubmitter[]) => {
        const comparator: ApiComparators = new ApiComparators();
        dialog.objects = value;
        dialog.objects.sort(comparator.compareSubmitters);
        dialog._data = new LinkDialogData();
        let index = 0;
        for (const submitter of dialog.objects) {
          dialog._data.items.push({
            index: index++,
            id: submitter.string,
            label: submitter.name + ' [' + submitter.string + ']'
          });
        }
      }
    );
  }

  linkSubmitter(data: LinkDialogData) {
    for (const item of data.selected) {
      const attribute: ApiAttribute = {
        type: 'submitterlink',
        string: item.id,
        tail: '',
        attributes: new Array<ApiAttribute>()
      };
      this.parent.attributes.push(attribute);
    }
    this.parent.save();
  }

  openUnlinkSubmitterDialog() {
    this.displayUnlinkSubmitterDialog = true;
  }

  onUnlinkSubmitterDialogClose() {
    this.displayUnlinkSubmitterDialog = false;
  }

  onUnlinkSubmitterDialogOpen(data: LinkDialogComponent) {
    this.submitterService.getAll(data.dataset).subscribe(
      (value: ApiSubmitter[]) => {
        const comparator: ApiComparators = new ApiComparators();
        data.objects = value;
        data.objects.sort(comparator.compareSubmitters);
        data._data = new LinkDialogData();
        let index = 0;
        for (const attribute of this.parent.attributes) {
          if (attribute.type === 'submitterlink') {
            index++;
            data._data.items.push({
              index: index,
              id: attribute.string,
              label: index + ' ' + this.find(attribute.string, data.objects) + ' [' + attribute.string + ']'
            });
          }
        }
      }
    );
  }

  private find(submitterId: string, submitters: Array<ApiSubmitter>): string {
    for (const submitter of submitters) {
      if (submitter.string === submitterId) {
        return submitter.name;
      }
    }
    return undefined;
  }

  unlinkSubmitter(data: LinkDialogData) {
    for (const item of data.selected) {
      this.spliceOutOneSubmitter(item);
    }
    this.parent.save();
  }

  spliceOutOneSubmitter(item: LinkItem) {
    let index = 0;
    for (const attribute of this.parent.attributes) {
      if (attribute.string === item.id) {
        this.parent.attributes.splice(index, 1);
        break;
      }
      index++;
    }
  }
}
