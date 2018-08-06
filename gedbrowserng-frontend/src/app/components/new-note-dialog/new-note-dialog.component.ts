import { Component, OnInit, Input, EventEmitter, Output, OnDestroy, OnChanges } from '@angular/core';

import { BaseDialog } from '../../bases';
import { NewNoteDialogData } from '../../models';

@Component({
  selector: 'app-new-note-dialog',
  templateUrl: './new-note-dialog.component.html',
  styleUrls: ['./new-note-dialog.component.css']
})
export class NewNoteDialogComponent
  extends BaseDialog<NewNoteDialogData, NewNoteDialogComponent>
  implements OnInit, OnChanges {

  _data: NewNoteDialogData;

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
