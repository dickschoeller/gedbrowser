import { SourceCreator } from '../../bases';
import { Component, Input } from '@angular/core';
import { MenuItem, SelectItem } from 'primeng/api';

import { HasAttributeList } from '../../interfaces';
import { ApiObject, ApiSource, ApiAttribute, LinkDialogData, LinkItem } from '../../models';
import { SourceService, NewSourceLinkService } from '../../services';
import { UrlBuilder, NewSourceHelper, ApiComparators } from '../../utils';
import { LinkDialogComponent } from '../link-dialog';
import { NewSourceDialogComponent } from '../new-source-dialog';

@Component({
  selector: 'app-sources',
  templateUrl: './sources.component.html',
  styleUrls: ['./sources.component.css']
})
export class SourcesComponent extends SourceCreator {
//  @Input() parentObject: ApiObject;
  @Input() parent: HasAttributeList;
  @Input() dataset: string;

  displaySourceDialog = false;
  displayLinkSourceDialog = false;
  displayUnlinkSourceDialog = false;

  sourcemenuitems: MenuItem[] = [
    {
      label: 'Add source',
      icon: 'fa-plus-circle',
      command: (event: Event) => { this.openCreateSourceDialog(); }
    },
    {
      label: 'Link source',
      icon: 'fa-link',
      command: (event: Event) => { this.openLinkSourceDialog(); }
    },
    {
      label: 'Unlink source',
      icon: 'fa-unlink',
      command: (event: Event) => { this.openUnlinkSourceDialog(); }
    },
  ];

  constructor(
    public sourceService: SourceService,
    public newSourceLinkService: NewSourceLinkService,
  ) {
    super(newSourceLinkService);
  }

  sourceUB(): UrlBuilder {
    // This would enable creating a source but not linking.
    return new UrlBuilder(this.dataset, 'sources');
  }

  sourceAnchor(): string {
    return undefined;
  }

  closeSourceDialog(): void {
    this.displaySourceDialog = false;
  }

  refreshSource(source: ApiSource): void {
    const attribute: ApiAttribute = {
      type: 'sourcelink',
      string: source.string,
      tail: '',
      attributes: new Array<ApiAttribute>()
    };
    this.parent.attributes.push(attribute);
    this.parent.save();
  }

  openCreateSourceDialog() {
    this.displaySourceDialog = true;
  }

  onSourceDialogClose() {
    this.displaySourceDialog = false;
  }

  onSourceDialogOpen(data: NewSourceDialogComponent) {
    if (data !== undefined) {
      const nsh = new NewSourceHelper();
      data._data = nsh.initNew('New Source');
    }
  }

  openLinkSourceDialog() {
    this.displayLinkSourceDialog = true;
  }

  onLinkSourceDialogClose() {
    this.displayLinkSourceDialog = false;
  }

  onLinkSourceDialogOpen(dialog: LinkDialogComponent) {
    this.sourceService.getAll(dialog.dataset).subscribe(
      (value: ApiSource[]) => {
        const comparator: ApiComparators = new ApiComparators();
        dialog.objects = value;
        dialog.objects.sort(comparator.compareSources);
        dialog._data = new LinkDialogData();
        let index = 0;
        for (const source of dialog.objects) {
          dialog._data.items.push({
            index: index++,
            id: source.string,
            label: source.title + ' [' + source.string + ']'
          });
        }
      }
    );
  }

  linkSource(data: LinkDialogData) {
    for (const item of data.selected) {
      const attribute: ApiAttribute = {
        type: 'sourcelink',
        string: item.id,
        tail: '',
        attributes: new Array<ApiAttribute>()
      };
      this.parent.attributes.push(attribute);
    }
    this.parent.save();
  }

  openUnlinkSourceDialog() {
    this.displayUnlinkSourceDialog = true;
  }

  onUnlinkSourceDialogClose() {
    this.displayUnlinkSourceDialog = false;
  }

  onUnlinkSourceDialogOpen(data: LinkDialogComponent) {
    this.sourceService.getAll(data.dataset).subscribe(
      (value: ApiSource[]) => {
        const comparator: ApiComparators = new ApiComparators();
        data.objects = value;
        data.objects.sort(comparator.compareSources);
        data._data = new LinkDialogData();
        let index = 0;
        for (const attribute of this.parent.attributes) {
          if (attribute.type === 'sourcelink') {
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

  private find(sourceId: string, sources: Array<ApiSource>): string {
    for (const source of sources) {
      if (source.string === sourceId) {
        return source.title;
      }
    }
    return undefined;
  }

  unlinkSource(data: LinkDialogData) {
    for (const item of data.selected) {
      this.spliceOutOneSource(item);
    }
    this.parent.save();
  }

  spliceOutOneSource(item: LinkItem) {
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
