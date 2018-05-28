import {OnInit, Component, Input} from '@angular/core';
import {MenuItem, SelectItem} from 'primeng/api';

import {HasAttributeList} from '../../interfaces';
import {SourceCreator} from '../../bases';
import {ApiAttribute, ApiSource} from '../../models';
import {NewSourceLinkService} from '../../services';
import {
  AttributeUtil, NameUtil, NewSourceHelper, StringUtil, UrlBuilder
} from '../../utils';

import {
  AttributeDialogData,
  AttributeDialogHelper,
  NewAttributeDialogComponent
} from '../attribute-dialog';
import {NewSourceDialogComponent} from '../new-source-dialog';

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
  attributeUtil = new AttributeUtil(this);
  attributeDialogHelper: AttributeDialogHelper = new AttributeDialogHelper(this);
  _data: AttributeDialogData;
  sourcemenuitems: MenuItem[] = [
    {
      label: 'Add source', icon: 'fa-plus-circle', command: (event: Event) => { this.openCreateSourceDialog(); }
    },
    {
      label: 'Link source', icon: 'fa-link', command: (event: Event) => { this.linkSource(); }
    },
  ];

  constructor(
    public newSourceLinkService: NewSourceLinkService
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

  linkSource() {
    //
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
}
