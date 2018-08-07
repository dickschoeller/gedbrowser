import { ApiAttribute, AttributeDialogData } from '../models';
import { StringUtil } from './string-util';

export class AttributeDialogHelper {
  constructor(public parent: any) {}

  public static dialogData(typeString: string): AttributeDialogData {
    return {
      insert: true, index: 0, type: typeString, text: '', date: '',
      place: '', note: '', originalType: '', originalText: '',
      originalDate: '', originalPlace: '', originalNote: ''
    };
  }

  buildData(insert: boolean) {
    let type = '';
    let text = '';
    if (this.parent.attribute.type === 'attribute') {
      type = this.parent.attribute.string;
      text = this.parent.attribute.tail;
    } else {
      type = StringUtil.capitalize(this.parent.attribute.type);
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
    return this.getBy(this.parent.attribute.attributes, typeInput, (attr) => attr.type.toLowerCase());
  }

  private getByString(stringInput: string) {
    return this.getBy(this.parent.attribute.attributes, stringInput, (attr) => attr.string);
  }

  private getBy(attributes: Array<ApiAttribute>, input: string, getField) {
    for (const attr of attributes) {
      if (getField(attr) === input) {
        return attr.tail;
      }
    }
  }

  private setByType(attribute, typeInput, valueInput) {
    if (StringUtil.isEmpty(valueInput)) {
      this.deleteBy(attribute, typeInput, (attr) => attr.type);
      return;
    }
    for (const attr of attribute.attributes) {
      if (attr.type.toLowerCase() === typeInput) {
        attr.string = valueInput;
        return;
      }
    }
    attribute.attributes.push({
      type: typeInput, string: valueInput,
      tail: '', attributes: new Array<ApiAttribute>()
    });
  }

  private setByString(attribute: ApiAttribute, stringInput, tailInput: string) {
    if (StringUtil.isEmpty(tailInput)) {
      this.deleteBy(attribute, stringInput, (attr) => attr.string);
      return;
    }
    for (const attr of attribute.attributes) {
      if (attr.string.toLowerCase() === stringInput) {
        attr.tail = tailInput;
        return;
      }
    }
    attribute.attributes.push({
      type: 'attribute', string: StringUtil.capitalize(stringInput),
      tail: tailInput, attributes: new Array<ApiAttribute>()
    });
  }

  private deleteBy(attribute: ApiAttribute, input: string, getField) {
    for (const attr of attribute.attributes) {
      if (getField(attr).toLowerCase() === input) {
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
