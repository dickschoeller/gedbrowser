import { ApiAttribute } from '../models';
export class AttributeDialogHelper {
  constructor(public parent: any) {}

  buildData() {
    const type = this.parent.attribute.string;
    const text = this.parent.attribute.tail;
    const date = this.date();
    const place = this.place();
    const note = this.note();
    const data = { type: type, text: text, date: date, place: place, note: note };
    return data;
  }

  date() {
    for (const attr of this.parent.attribute.attributes) {
      if (attr.type.toLowerCase() === 'date') {
        return attr.string;
      }
    }
  }

  place() {
    for (const attr of this.parent.attribute.attributes) {
      if (attr.type.toLowerCase() === 'place') {
        return attr.string;
      }
    }
  }

  note() {
    for (const attr of this.parent.attribute.attributes) {
      if (attr.string === 'Note') {
        return attr.tail;
      }
    }
  }

  populateAttribute(data: any) {
      this.parent.attribute.string = data.type;
      this.parent.attribute.tail = data.text;
      this.setDate(this.parent.attribute, data.date);
      this.setPlace(this.parent.attribute, data.place);
      this.setNote(this.parent.attribute, data.note);
  }

  setDate(attribute, date) {
    if (date === null || date === undefined) {
      this.deleteDate(attribute);
      return;
    }
    for (const attr of this.parent.attribute.attributes) {
      if (attr.type.toLowerCase() === 'date') {
        attr.string = date;
        return;
      }
    }
    const newDate: ApiAttribute = new ApiAttribute();
    newDate.type = 'Date';
    newDate.string = date;
    this.parent.attribute.attributes.push(newDate);
  }

  deleteDate(attribute) {
    for (const attr of this.parent.attribute.attributes) {
      if (attr.type.toLowerCase() === 'date') {
        const index = this.parent.attribute.attributes.indexOf(attr);
        this.parent.attribute.attributes.splice(index, 1);
        return;
      }
    }
  }


  setPlace(attribute, place) {
    if (place === null || place === undefined) {
      this.deletePlace(attribute);
      return;
    }
    for (const attr of this.parent.attribute.attributes) {
      if (attr.type.toLowerCase() === 'place') {
        attr.string = place;
        return;
      }
    }
    const newPlace: ApiAttribute = new ApiAttribute();
    newPlace.type = 'Place';
    newPlace.string = place;
    this.parent.attribute.attributes.push(newPlace);
  }

  deletePlace(attribute) {
    for (const attr of this.parent.attribute.attributes) {
      if (attr.type.toLowerCase() === 'place') {
        const index = this.parent.attribute.attributes.indexOf(attr);
        this.parent.attribute.attributes.splice(index, 1);
        return;
      }
    }
  }


  setNote(attribute, note) {
    if (note === null || note === undefined) {
      this.deleteNote(attribute);
      return;
    }
    for (const attr of this.parent.attribute.attributes) {
      if (attr.string.toLowerCase() === 'note') {
        attr.string = note;
        return;
      }
    }
    const newNote: ApiAttribute = new ApiAttribute();
    newNote.type = 'Note';
    newNote.string = note;
    this.parent.attribute.attributes.push(newNote);
  }

  deleteNote(attribute) {
      for (const attr of this.parent.attribute.attributes) {
        if (attr.string.toLowerCase() === 'note') {
          const index = this.parent.attribute.attributes.indexOf(attr);
          this.parent.attribute.attributes.splice(index, 1);
          return;
        }
      }
  }
}
