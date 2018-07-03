import {ApiAttribute} from '../../models';
import {StringUtil} from '../../utils/string-util';
import {AttributeDialogData} from './attribute-dialog-data';

export class AttributeDialogHelper {
  constructor(public parent: any) {}

  buildData(insert: boolean) {
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
    const note = this.getByString('Note');
    const data: AttributeDialogData = {
      insert: insert, index: this.parent.index, type: type, text: text,
      date: date, place: place, note: note, originalType: type,
      originalText: text, originalDate: date, originalPlace: place,
      originalNote: note, };
    return data;
  }

  populateParentAttribute(data: AttributeDialogData) {
    this.populateAttribute(this.parent.attribute, data);
  }

  populateNewAttribute(data: AttributeDialogData): ApiAttribute {
    const attribute = new ApiAttribute();
    this.populateAttribute(attribute, data);
    return attribute;
  }

  private populateAttribute(attribute: ApiAttribute, data: AttributeDialogData) {
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

  public simpleAttribute(type: string, text: string): ApiAttribute {
    return this.populateNewAttribute({
      insert: true, index: 0,
      type: type, text: text, date: '', place: '', note: '',
      originalType: type, originalText: text, originalDate: '',
      originalPlace: '', originalNote: ''
    });
  }
}
