import {Component, OnInit, Input, Output, EventEmitter, OnDestroy, OnChanges} from '@angular/core';
import {SelectItem} from 'primeng/api';

import {BaseDialogComponent} from '../base-dialog/base-dialog.component';

import {AttributeDialogData} from './attribute-dialog-data';

@Component({
  selector: 'app-new-attribute-dialog',
  templateUrl: './new-attribute-dialog.component.html',
  styleUrls: ['./new-attribute-dialog.component.css']
})
export class NewAttributeDialogComponent
  extends BaseDialogComponent<AttributeDialogData, NewAttributeDialogComponent>
  implements OnInit, OnChanges {
  @Input() options;
  data = function(): AttributeDialogData {
    return {
      insert: true, index: 0, type: 'Name', text: '', date: '',
      place: '', note: '', originalType: '', originalText: '',
      originalDate: '', originalPlace: '', originalNote: ''
    };
  };

  constructor() {
    super();
  }

  ngOnInit() {
    this._data = this.p.defaultData();
  }

  ngOnChanges() {
    this._data = this.p.defaultData();
  }

  open() {
    this._data = this.p.defaultData();
    this.emitOpen.emit(this);
  }

  close() {
    this.emitClose.emit();
    this._data = this.p.defaultData();
  }

  ok() {
    this.emitOK.emit(this._data);
    this.emitClose.emit();
    this._data = this.p.defaultData();
  }

  cancel() {
    this.emitClose.emit();
    this._data = this.p.defaultData();
  }
}
