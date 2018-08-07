import { ApiAttribute } from '../models';

import { NameUtil } from './name-util';
import { StringUtil } from './string-util';

export class AttributeAnalyzer {
  constructor(private parent: any) {}

  label() {
    if (this.parent.attribute.type === 'attribute') {
      return this.parent.attribute.string;
    }
    return StringUtil.titleCase(this.parent.attribute.type);
  }

  contents() {
    if (this.parent.attribute.type === 'attribute') {
      return this.parent.attribute.tail;
    }
    if (this.parent.attribute.type === 'name') {
      return NameUtil.cleanup(this.parent.attribute.string);
    }
    return this.parent.attribute.string;
  }

  editable(): boolean {
    if (this.label() === 'Reference Number' || this.label() === 'Changed') {
      return false;
    }
    return true;
  }

  first(): boolean {
    return this.parent.attributes.indexOf(this.parent.attribute) === 0;
  }

  last(): boolean {
    return this.isLast(this.parent.attributes, this.parent.attribute);
  }

  isLast(attributes: Array<ApiAttribute>, attribute: ApiAttribute): boolean {
    const index = attributes.indexOf(attribute);
    const length = attributes.length;
    if (index === (length - 1)) {
      return true;
    }
    if (index === (length - 2)) {
      return ((attributes[length - 1].string === 'Reference Number')
        || (attributes[length - 1].string === 'Changed'));
    }
    if (index === (length - 3)) {
      return ((attributes[length - 2].string === 'Reference Number')
        && (attributes[length - 1].string === 'Changed'));
    }
    return false;
  }

  lastIndex(): number {
    let index = 0;
    if (this.parent === null || this.parent.attributes === null) {
      return 0;
    }
    for (const attribute of this.parent.attributes) {
      if (this.isLast(this.parent.attributes, attribute)) {
        return index;
      }
      index++;
    }
    return 0;
  }

  href() {
    return this.hrefit(this.parent.dataset, this.parent.attribute);
  }

  hrefit(dataset: string, attribute: ApiAttribute) {
    if (attribute.type === 'sourcelink') {
      return '#/' + dataset + '/sources/' + attribute.string;
    }
    if (attribute.type === 'submitterlink') {
      return '#/' + dataset + '/submitters/' + attribute.string;
    }
    if (attribute.type === 'submissionlink') {
      return '#/' + dataset + '/submissions/' + attribute.string;
    }
    if (attribute.type === 'notelink') {
      return '#/' + dataset + '/notes/' + attribute.string;
    }
    if (attribute.string === 'File') {
      return attribute.tail;
    }
    return null;
  }

}
