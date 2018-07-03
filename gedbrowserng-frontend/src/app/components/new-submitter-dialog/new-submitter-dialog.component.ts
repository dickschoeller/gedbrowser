import { Component, OnInit, Input, EventEmitter, Output, OnDestroy, OnChanges } from '@angular/core';

import { BaseDialog } from '../../bases';
import { NewSubmitterDialogData } from '../../models/new-submitter-dialog-data';

@Component({
  selector: 'app-new-submitter-dialog',
  templateUrl: './new-submitter-dialog.component.html',
  styleUrls: ['./new-submitter-dialog.component.css']
})
export class NewSubmitterDialogComponent
  extends BaseDialog<NewSubmitterDialogData, NewSubmitterDialogComponent>
  implements OnInit, OnChanges {

  _data: NewSubmitterDialogData;

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
