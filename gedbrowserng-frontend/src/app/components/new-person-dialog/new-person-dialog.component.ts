import {Component, OnInit, Input, EventEmitter, Output, OnDestroy, OnChanges} from '@angular/core';

import {BaseDialogComponent} from '../base-dialog/base-dialog.component';

import {NewPersonDialogData} from '../../models/new-person-dialog-data';

@Component({
  selector: 'app-new-person-dialog',
  templateUrl: './new-person-dialog.component.html',
  styleUrls: ['./new-person-dialog.component.css']
})
export class NewPersonDialogComponent
  extends BaseDialogComponent<NewPersonDialogData, NewPersonDialogComponent>
  implements OnInit, OnChanges {

  _data: NewPersonDialogData;

  constructor() {
    super();
  }

  ngOnInit() {
    this.emitOpen.emit(this);
  }

  ngOnChanges() {
    this.emitOpen.emit(this);
  }

  open() {
    this.emitOpen.emit(this);
  }

  close() {
    this.emitClose.emit();
    this.emitOpen.emit(this);
  }

  ok() {
    this.emitOK.emit(this._data);
    this.emitClose.emit();
    this.emitOpen.emit(this);
  }

  cancel() {
    this.emitClose.emit();
    this.emitOpen.emit(this);
  }
}
