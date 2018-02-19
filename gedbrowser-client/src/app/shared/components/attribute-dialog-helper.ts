import { ApiAttribute } from '../models';
import { StringUtil } from '../util/string-util';
export class AttributeDialogHelper {
  constructor(public parent: any) {}

  buildData() {
    let type = '';
    let text = '';
    if (this.parent.attribute.type === 'attribute') {
      type = this.parent.attribute.string;
      text = this.parent.attribute.tail;
    } else {
      type = new StringUtil().capitalize(this.parent.attribute.type);
      text = this.parent.attribute.string;
    }
    const date = this.getByType('date');
    const place = this.getByType('place');
    const note = this.getByString('note');
    const data = { type: type, text: text, date: date, place: place, note: note };
    return data;
  }

  populateParentAttribute(data: any) {
    this.populateAttribute(this.parent.attribute, data);
  }

  populateNewAttribute(data: any): ApiAttribute {
    const attribute = new ApiAttribute();
    this.populateAttribute(attribute, data);
    return attribute;
  }

  private populateAttribute(attribute: ApiAttribute, data: any) {
    if (data.type.toLowerCase() === 'name') {
      attribute.type = data.type.toLowerCase();
      attribute.string = data.text;
      attribute.tail = '';
    } else {
      attribute.type = 'attribute';
      attribute.string = data.type;
      attribute.tail = data.text;
    }
    if (attribute.attributes === undefined) {
      attribute.attributes = new Array<ApiAttribute>();
    }
    this.setByType(attribute, 'date', data.date);
    this.setByType(attribute, 'place', data.place);
    this.setByString(attribute, 'note', data.note);
  }

  private getByType(typeInput: string) {
    for (const attr of this.parent.attribute.attributes) {
      if (attr.type.toLowerCase() === typeInput) {
        return attr.string;
      }
    }
  }

  private getByString(stringInput: string) {
    for (const attr of this.parent.attribute.attributes) {
      if (attr.string === stringInput) {
        return attr.tail;
      }
    }
  }

  private setByType(attribute, typeInput, valueInput) {
    if (valueInput === null || valueInput === undefined || valueInput === '') {
      this.deleteByType(attribute, typeInput);
      return;
    }
    for (const attr of attribute.attributes) {
      if (attr.type.toLowerCase() === typeInput) {
        attr.string = valueInput;
        return;
      }
    }
    const newAttr: ApiAttribute = new ApiAttribute();
    newAttr.type = typeInput;
    newAttr.string = valueInput;
    attribute.attributes.push(newAttr);
  }

  alertAttribute(arg0: ApiAttribute): any {
    alert(this.dumpAttribute(arg0));
  }

  dumpAttribute(attribute: ApiAttribute, indent?: string): string {
    let i = '';
    if (indent) {
      i = indent;
    }
    const v = i + '{\n'
      + i + '  type : "' + attribute.type + '"\n'
      + i + '  string : "' + attribute.string + '"\n'
      + i + '  tail : "' + attribute.tail + '"\n'
      + i + '  attributes : [' + this.dumpAttributes(attribute.attributes, i + '    ') + ']\n'
      + i + '}'
      ;
    return v;
  }

  dumpAttributes(attributes: Array<ApiAttribute>, indent: string): string {
    if (attributes === undefined || attributes.length === 0) {
      return '';
    }
    let val = '\n';
    for (const attribute of attributes) {
      val = val + this.dumpAttribute(attribute, indent) + '\n';
    }
    return val;
  }

  private setByString(attribute: ApiAttribute, stringInput, tailInput: string) {
    if (tailInput === null || tailInput === undefined || tailInput === '') {
      this.deleteByString(attribute, stringInput);
      return;
    }
    for (const attr of attribute.attributes) {
      if (attr.string.toLowerCase() === stringInput) {
        attr.tail = tailInput;
        return;
      }
    }
    const newAttr: ApiAttribute = new ApiAttribute();
    newAttr.type = 'attribute';
    newAttr.string = new StringUtil().capitalize(stringInput);
    newAttr.tail = tailInput;
    attribute.attributes.push(newAttr);
  }

  private deleteByType(attribute: ApiAttribute, typeInput: string) {
    for (const attr of attribute.attributes) {
      if (attr.type.toLowerCase() === typeInput) {
        const index = attribute.attributes.indexOf(attr);
        attribute.attributes.splice(index, 1);
        return;
      }
    }
  }

  private deleteByString(attribute: ApiAttribute, stringInput: string) {
    for (const attr of attribute.attributes) {
      if (attr.string.toLowerCase() === stringInput) {
        const index = attribute.attributes.indexOf(attr);
        attribute.attributes.splice(index, 1);
        return;
      }
    }
  }
}
