import {OnInit, Component, Input} from '@angular/core';
import {MenuItem, SelectItem} from 'primeng/api';

import {HasAttributeList} from '../../interfaces';
import {SourceCreator} from '../../bases';
import {ApiAttribute, ApiSource, LinkSourceDialogData, LinkSourceItem} from '../../models';
import {NewSourceLinkService, SourceService} from '../../services';
import {
  AttributeUtil, NameUtil, NewSourceHelper, StringUtil, UrlBuilder
} from '../../utils';

import {
  AttributeDialogData,
  AttributeDialogHelper,
  NewAttributeDialogComponent
} from '../attribute-dialog';
import {NewSourceDialogComponent} from '../new-source-dialog';
import {LinkSourceDialogComponent} from '../link-source-dialog/';

@Component({
  selector: 'app-attribute-list-item',
  templateUrl: './attribute-list-item.component.html',
  styleUrls: ['./attribute-list-item.component.css']
})
export class AttributeListItemComponent extends SourceCreator implements OnInit {
  @Input() attribute: ApiAttribute;
  @Input() attributes: Array<ApiAttribute>;
  @Input() index: number;
  @Input() parent: HasAttributeList;
  @Input() dataset: string;

  displayAttributeDialog = false;
  displaySourceDialog = false;
  displayLinkSourceDialog = false;
  displayUnlinkSourceDialog = false;
  attributeUtil = new AttributeUtil(this);
  attributeDialogHelper: AttributeDialogHelper = new AttributeDialogHelper(this);
  _data: AttributeDialogData;
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
    public newSourceLinkService: NewSourceLinkService,
    private sourceService: SourceService,
  ) {
    super(newSourceLinkService);
  }

  ngOnInit() {
  }

  edit() {
    this.displayAttributeDialog = true;
  }

  defaultData(): AttributeDialogData {
    const adh: AttributeDialogHelper = new AttributeDialogHelper(this);
    return adh.buildData(false);
  }

  onAttributeDialogOpen(data: NewAttributeDialogComponent) {
    data.data = this.defaultData;
  }

  onAttributeDialogOK(data: AttributeDialogData) {
    if (data != null) {
      this.attributeDialogHelper.populateParentAttribute(data);
      this.parent.save();
    }
  }

  onAttributeDialogClose() {
    this.displayAttributeDialog = false;
  }

  options(): Array<SelectItem> {
    return this.parent.options();
  }

  delete(): void {
    const index = this.attributes.indexOf(this.attribute);
    this.attributes.splice(index, 1);
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
        data.sources = value;
        data.sources.sort(data.compare);
        data._data = {
          items: new Array<LinkSourceItem>(),
          selected: new Array<LinkSourceItem>()
        };
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
        data.sources = value;
        data.sources.sort(data.compare);
        data._data = {
          items: new Array<LinkSourceItem>(),
          selected: new Array<LinkSourceItem>()
        };
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
      this.attribute.attributes.forEach((attribute, index) => {
        if (attribute.string === item.id) {
          this.attribute.attributes.splice(index, 1);
          return;
        }
      });
    }
    this.parent.save();
  }
}
