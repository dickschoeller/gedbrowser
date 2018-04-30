import {Component, OnInit, Input, Output, EventEmitter, OnDestroy, OnChanges} from '@angular/core';
import {SelectItem} from 'primeng/api';

import {AttributeDialogData} from './attribute-dialog-data';

@Component({
  selector: 'app-new-attribute-dialog',
  templateUrl: './new-attribute-dialog.component.html',
  styleUrls: ['./new-attribute-dialog.component.css']
})
export class NewAttributeDialogComponent implements OnInit, OnChanges, OnDestroy {
  _display = false;
  @Input() set display(value: boolean) {
    this._display = value;
  }

  get display(): boolean {
    return this._display;
  }

  @Input() p: any;
  @Input() options;
  @Output() onOK = new EventEmitter<AttributeDialogData>();
  @Output() onClose = new EventEmitter<void>();
  @Output() onOpen = new EventEmitter<NewAttributeDialogComponent>();
  _data: AttributeDialogData;
  data = function(): AttributeDialogData {
    return {
      insert: true, index: 0, type: 'Name', text: '', date: '',
      place: '', note: '', originalType: '', originalText: '',
      originalDate: '', originalPlace: '', originalNote: ''
    };
  };

  constructor() { }

  ngOnInit() {
    this._data = this.p.defaultData();
  }

  ngOnChanges() {
    this._data = this.p.defaultData();
  }

  open() {
    this._data = this.p.defaultData();
    this.onOpen.emit(this);
  }

  close() {
    this.onClose.emit();
    this._data = this.p.defaultData();
  }

  ok() {
    this.onOK.emit(this._data);
    this.onClose.emit();
    this._data = this.p.defaultData();
  }

  cancel() {
    this.onClose.emit();
    this._data = this.p.defaultData();
  }

  ngOnDestroy() {
    this.onClose.unsubscribe();
    this.onOK.unsubscribe();
    this.onOpen.unsubscribe();
  }
}
