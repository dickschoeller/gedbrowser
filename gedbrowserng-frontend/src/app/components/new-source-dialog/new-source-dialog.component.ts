import {Component, OnInit, Input, EventEmitter, Output, OnDestroy, OnChanges} from '@angular/core';

import {BaseDialogComponent} from '../base-dialog/base-dialog.component';

import {NewSourceDialogData} from '../../models/new-source-dialog-data';

@Component({
  selector: 'app-new-source-dialog',
  templateUrl: './new-source-dialog.component.html',
  styleUrls: ['./new-source-dialog.component.css']
})
export class NewSourceDialogComponent
  extends BaseDialogComponent<NewSourceDialogData, NewSourceDialogComponent>
  implements OnInit, OnChanges {

  _data: NewSourceDialogData = {title: '', abbreviation: '', text: ''};

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
