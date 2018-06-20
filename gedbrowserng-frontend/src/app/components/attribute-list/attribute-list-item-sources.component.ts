import { Component, OnInit, Input } from '@angular/core';
import { MenuItem, SelectItem } from 'primeng/api';

import { SourceCreator } from '../../bases';
import { HasAttributeList } from '../../interfaces';
import { ApiAttribute, ApiSource, LinkSourceDialogData, LinkSourceItem } from '../../models';
import { NewSourceLinkService, SourceService } from '../../services';
import { ApiComparators, NewSourceHelper, UrlBuilder } from '../../utils';

import { LinkSourceDialogComponent } from '../link-source-dialog';
import { NewSourceDialogComponent } from '../new-source-dialog';

@Component({
  selector: 'app-attribute-list-item-sources',
  templateUrl: './attribute-list-item-sources.component.html',
  styleUrls: ['./attribute-list-item-sources.component.css']
})
export class AttributeListItemSourcesComponent extends SourceCreator implements OnInit {
  @Input() attribute: ApiAttribute;
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

  ngOnInit() {
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

  closeSourceDialog(): void {
    this.displaySourceDialog = false;
  }

  sourceUB(): UrlBuilder {
    // This would enable creating a source but not linking.
    return new UrlBuilder(this.dataset, 'sources');
  }

  sourceAnchor(): string {
    return undefined;
  }

  refreshSource(source: ApiSource): void {
    const attribute: ApiAttribute = {
      type: 'sourcelink',
      string: source.string,
      tail: '',
      attributes: new Array<ApiAttribute>()
    };
    this.attribute.attributes.push(attribute);
    this.parent.save();
  }

  openLinkSourceDialog() {
    this.displayLinkSourceDialog = true;
  }

  onLinkSourceDialogClose() {
    this.displayLinkSourceDialog = false;
  }

  onLinkSourceDialogOpen(data: LinkSourceDialogComponent) {
    this.sourceService.getAll(data.dataset).subscribe(
      (value: ApiSource[]) => {
        const comparator: ApiComparators = new ApiComparators();
        data.sources = value;
        data.sources.sort(comparator.compareSources);
        data._data = new LinkSourceDialogData();
        let index = 0;
        for (const source of data.sources) {
          data._data.items.push({
            index: index++,
            id: source.string,
            title: source.title + ' [' + source.string + ']'
          });
        }
      }
    );
  }

  linkSource(data: LinkSourceDialogData) {
    for (const item of data.selected) {
      const attribute: ApiAttribute = {
        type: 'sourcelink',
        string: item.id,
        tail: '',
        attributes: new Array<ApiAttribute>()
      };
      this.attribute.attributes.push(attribute);
    }
    this.parent.save();
  }

  openUnlinkSourceDialog() {
    this.displayUnlinkSourceDialog = true;
  }

  onUnlinkSourceDialogClose() {
    this.displayUnlinkSourceDialog = false;
  }

  onUnlinkSourceDialogOpen(data: LinkSourceDialogComponent) {
    this.sourceService.getAll(data.dataset).subscribe(
      (value: ApiSource[]) => {
        const comparator: ApiComparators = new ApiComparators();
        data.sources = value;
        data.sources.sort(comparator.compareSources);
        data._data = new LinkSourceDialogData();
        let index = 0;
        for (const attribute of this.attribute.attributes) {
          if (attribute.type === 'sourcelink') {
            index++;
            data._data.items.push({
              index: index,
              id: attribute.string,
              title: index + ' ' + this.find(attribute.string, data.sources) + ' [' + attribute.string + ']'
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

  unlinkSource(data: LinkSourceDialogData) {
    for (const item of data.selected) {
      this.spliceOutOneSource(item);
    }
    this.parent.save();
  }

  spliceOutOneSource(item: LinkSourceItem) {
    let index = 0;
    for (const attribute of this.attribute.attributes) {
      if (attribute.string === item.id) {
        this.attribute.attributes.splice(index, 1);
        break;
      }
      index++;
    }
  }
}
