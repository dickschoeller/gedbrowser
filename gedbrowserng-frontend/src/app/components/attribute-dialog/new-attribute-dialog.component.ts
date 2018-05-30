import {Component, OnInit, Input, Output, EventEmitter, OnDestroy, OnChanges} from '@angular/core';
import {SelectItem} from 'primeng/api';

import {BaseDialog} from '../../bases';

import {AttributeDialogData} from './attribute-dialog-data';

@Component({
  selector: 'app-new-attribute-dialog',
  templateUrl: './new-attribute-dialog.component.html',
  styleUrls: ['./new-attribute-dialog.component.css']
})
export class NewAttributeDialogComponent
  extends BaseDialog<AttributeDialogData, NewAttributeDialogComponent>
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
    this.emitOpen.emit(this);
  }
}
