import { Component, OnInit, Input, EventEmitter, Output, OnDestroy, OnChanges } from '@angular/core';

import { BaseDialog } from '../../bases';
import { NewSourceDialogData } from '../../models';

@Component({
  selector: 'app-new-source-dialog',
  templateUrl: './new-source-dialog.component.html',
  styleUrls: ['./new-source-dialog.component.css']
})
export class NewSourceDialogComponent
  extends BaseDialog<NewSourceDialogData, NewSourceDialogComponent>
  implements OnInit, OnChanges {

  _data: NewSourceDialogData;

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
