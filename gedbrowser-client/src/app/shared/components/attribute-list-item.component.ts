import { Component, OnInit, Input } from '@angular/core';
import { ApiAttribute } from '../models';
import { StringUtil, NameUtil } from '../util';
import { AttributeDialogComponent } from './attribute-dialog.component';
import { MatDialogRef, MatDialog } from '@angular/material';

@Component({
  selector: 'app-attribute-list-item',
  templateUrl: './attribute-list-item.component.html',
  styleUrls: ['./attribute-list-item.component.css']
})
export class AttributeListItemComponent implements OnInit {
  @Input() attribute: ApiAttribute;
  @Input() attributes: Array<ApiAttribute>;

  constructor(public dialog: MatDialog) { }

  ngOnInit() {
  }

  label() {
    if (this.attribute.type === 'attribute') {
      return this.attribute.string;
    }
    return new StringUtil().titleCase(this.attribute.type);
  }

  contents() {
    if (this.attribute.type === 'attribute') {
      return this.attribute.tail;
    }
    if (this.attribute.type === 'name') {
      return new NameUtil().cleanup(this.attribute.string);
    }
    return this.attribute.string;
  }

  editable(): boolean {
    if (this.label() === 'Reference Number' || this.label() === 'Changed') {
      return false;
    }
    return true;
  }

  first(): boolean {
    return this.attributes.indexOf(this.attribute) === 0;
  }

  last(): boolean {
    if (this.attributes.indexOf(this.attribute) === (this.attributes.length - 1)) {
      return true;
    }
    if (this.attributes.indexOf(this.attribute) === (this.attributes.length - 2)) {
      if (this.attributes[this.attributes.length - 1].string === 'Reference Number') {
        return true;
      }
      if (this.attributes[this.attributes.length - 1].string === 'Changed') {
        return true;
      }
    }
    if (this.attributes.indexOf(this.attribute) === (this.attributes.length - 3)) {
      if (this.attributes[this.attributes.length - 2].string === 'Reference Number') {
        if (this.attributes[this.attributes.length - 1].string === 'Changed') {
          return true;
        }
      }
    }
    return false;
  }

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
