import {Component, OnInit, Input, EventEmitter, Output, OnDestroy} from '@angular/core';

@Component({
  selector: 'app-base-dialog',
  templateUrl: './base-dialog.component.html',
  styleUrls: ['./base-dialog.component.css']
})
export class BaseDialogComponent<D, C> implements OnInit, OnDestroy {
  @Input() p: any;
  @Input() set display(value: boolean) {
    this._display = value;
  }
  get display(): boolean {
    return this._display;
  }

  @Output() onOK = new EventEmitter<D>();
  @Output() onClose = new EventEmitter<void>();
  @Output() onOpen = new EventEmitter<C>();

  _display = false;
  _data: D;

  constructor() {}

  ngOnInit() {
  }

  ngOnDestroy() {
    this.onClose.unsubscribe();
    this.onOK.unsubscribe();
    this.onOpen.unsubscribe();
  }

}
