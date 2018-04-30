import {Component, OnInit, Input, EventEmitter, Output, OnDestroy, OnChanges} from '@angular/core';

import {BaseDialogComponent} from '../base-dialog/base-dialog.component';

import {NewPersonDialogData} from './new-person-dialog-data';

@Component({
  selector: 'app-new-person-dialog2',
  templateUrl: './new-person-dialog2.component.html',
  styleUrls: ['./new-person-dialog2.component.css']
})
export class NewPersonDialog2Component
  extends BaseDialogComponent<NewPersonDialogData, NewPersonDialog2Component>
  implements OnInit, OnChanges {

  _data: NewPersonDialogData;

  constructor() {
    super();
  }

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
}
