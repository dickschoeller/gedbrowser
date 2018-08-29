import { Component, OnInit, Input, EventEmitter, Output, OnDestroy, OnChanges } from '@angular/core';
import { SelectItem } from 'primeng/api';

import { BaseDialog } from '../../bases';
import { MultimediaDialogData, MultimediaFileData, MultimediaFormat, MultimediaSourceType } from '../../models';

@Component({
  selector: 'app-multimedia-dialog',
  templateUrl: './multimedia-dialog.component.html',
  styleUrls: ['./multimedia-dialog.component.css']
})
export class MultimediaDialogComponent
  extends BaseDialog<MultimediaDialogData, MultimediaDialogComponent>
  implements OnInit, OnChanges {

  _data: MultimediaDialogData;

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

  formats(): Array<SelectItem> {
    const formats: Array<SelectItem> = new Array<SelectItem>();
    for (const formatString of Object.keys(MultimediaFormat)) {
      formats.push({ label: formatString, value: MultimediaFormat[formatString] });
    }
    return formats;
  }

  sourceTypes(): Array<SelectItem> {
    const sourceTypes: Array<SelectItem> = new Array<SelectItem>();
    for (const sourceTypeString of Object.keys(MultimediaSourceType)) {
      sourceTypes.push({ label: sourceTypeString, value: MultimediaSourceType[sourceTypeString] });
    }
    return sourceTypes;
  }
}
