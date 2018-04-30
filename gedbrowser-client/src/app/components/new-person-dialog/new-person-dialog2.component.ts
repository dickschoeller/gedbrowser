import {Component, OnInit, Input, EventEmitter, Output, OnDestroy, OnChanges} from '@angular/core';

import {NewPersonDialogData} from './new-person-dialog-data';

@Component({
  selector: 'app-new-person-dialog2',
  templateUrl: './new-person-dialog2.component.html',
  styleUrls: ['./new-person-dialog2.component.css']
})
export class NewPersonDialog2Component implements OnInit, OnChanges, OnDestroy {
  _display = false;
  @Input() set display(value: boolean) {
    this._display = value;
  }

  get display(): boolean {
    return this._display;
  }
  @Input() p: any;
  @Output() onOK = new EventEmitter<NewPersonDialogData>();
  @Output() onClose = new EventEmitter<void>();
  @Output() onOpen = new EventEmitter<NewPersonDialog2Component>();

  _data: NewPersonDialogData;

  constructor() { }

  ngOnInit() {
    this.onOpen.emit(this);
  }

  ngOnChanges() {
    this.onOpen.emit(this);
  }

  open() {
    this.onOpen.emit(this);
  }

  close() {
    this.onClose.emit();
    this.onOpen.emit(this);
  }

  ok() {
    this.onOK.emit(this._data);
    this.onClose.emit();
    this.onOpen.emit(this);
  }

  cancel() {
    this.onClose.emit();
    this.onOpen.emit(this);
  }

  ngOnDestroy() {
    this.onClose.unsubscribe();
    this.onOK.unsubscribe();
    this.onOpen.unsubscribe();
  }
}
