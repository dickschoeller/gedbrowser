import {Component, Input} from '@angular/core';
import {ApiAttribute} from '../models';
import {StringUtil, NameUtil, AttributeUtil} from '../util';
import {AttributeDialogComponent} from './attribute-dialog.component';
import {MatDialogRef, MatDialog} from '@angular/material';

@Component({
  selector: 'app-attribute-list-item',
  templateUrl: './attribute-list-item.component.html',
  styleUrls: ['./attribute-list-item.component.css']
})
export class AttributeListItemComponent {
  @Input() attribute: ApiAttribute;
  @Input() attributes: Array<ApiAttribute>;
  attributeUtil = new AttributeUtil(this);

  constructor(public dialog: MatDialog) { }

  edit(): void {
    const config = {
      width: '500px',
      height: '600px',
      data: this.buildData()
    };
    const dialogRef: MatDialogRef<AttributeDialogComponent> =
      this.dialog.open(AttributeDialogComponent, config);

    dialogRef.afterClosed().subscribe(result => {
      const data = result;
    });
  }

  buildData() {
    const type = this.attribute.string;
    const text = this.attribute.tail;
    const date = this.date();
    const place = this.place();
    const note = this.note();
    const data = { type: type, text: text, date: date, place: place, note: note };
    return data;
  }

  date() {
    for (const attr of this.attribute.attributes) {
      if (attr.type.toLowerCase() === 'date') {
        return attr.string;
      }
    }
  }

  place() {
    for (const attr of this.attribute.attributes) {
      if (attr.type.toLowerCase() === 'place') {
        return attr.string;
      }
    }
  }

  note() {
    for (const attr of this.attribute.attributes) {
      if (attr.string === 'Note') {
        return attr.tail;
      }
    }
  }

  moveUp(): void {
    const index = this.attributes.indexOf(this.attribute);
    this.attributes.splice(index - 1, 0, this.attributes.splice(index, 1)[0]);
    alert(this.dumpAttributes());
  }

  moveDown(): void {
    const index = this.attributes.indexOf(this.attribute);
    this.attributes.splice(index + 1, 0, this.attributes.splice(index, 1)[0]);
    alert(this.dumpAttributes());
  }

  delete(): void {
    const index = this.attributes.indexOf(this.attribute);
    this.attributes.splice(index, 1);
    alert(this.dumpAttributes());
  }

  dumpAttribute(): string {
    let result = '';
    for (const attr of this.attribute.attributes) {
      result = result + 'type:' + attr.type + ', string: ' + attr.string + ', tail: ' + attr.tail + '\n';
    }
    return result;
  }

  dumpAttributes(): string {
    let result = '';
    for (const attr of this.attributes) {
      result = result + attr.type + ' ' + attr.string + ' ' + attr.tail + '\n';
    }
    return result;
  }
}
