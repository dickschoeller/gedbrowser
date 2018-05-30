import {Component, OnInit, Input, EventEmitter, Output, OnDestroy, OnChanges} from '@angular/core';

import {BaseDialog} from '../../bases';

import {NewPersonDialogData} from '../../models';

@Component({
  selector: 'app-new-person-dialog',
  templateUrl: './new-person-dialog.component.html',
  styleUrls: ['./new-person-dialog.component.css']
})
export class NewPersonDialogComponent
  extends BaseDialog<NewPersonDialogData, NewPersonDialogComponent>
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
}
