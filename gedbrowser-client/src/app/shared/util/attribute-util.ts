import { ApiAttribute } from '../models';
import { NameUtil } from './name-util';
import { StringUtil } from './string-util';
export class AttributeUtil {
  constructor(private parent: any) {}

  label() {
    if (this.parent.attribute.type === 'attribute') {
      return this.parent.attribute.string;
    }
    return new StringUtil().titleCase(this.parent.attribute.type);
  }

  contents() {
    if (this.parent.attribute.type === 'attribute') {
      return this.parent.attribute.tail;
    }
    if (this.parent.attribute.type === 'name') {
      return new NameUtil().cleanup(this.parent.attribute.string);
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
    const attribute: ApiAttribute = this.parent.attribute;
    const attributes: Array<ApiAttribute> = this.parent.attributes;
    const index = attributes.indexOf(attribute);
    const length = attributes.length;
    if (index === (length - 1)) {
      return true;
    }
    if (index === (length - 2)) {
      if (attributes[length - 1].string === 'Reference Number') {
        return true;
      }
      if (attributes[length - 1].string === 'Changed') {
        return true;
      }
    }
    if (index === (length - 3)) {
      if (attributes[length - 2].string === 'Reference Number') {
        if (attributes[length - 1].string === 'Changed') {
          return true;
        }
      }
    }
    return false;
  }
}
