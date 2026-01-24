import { Component, OnInit, OnChanges, Input, Output, EventEmitter } from '@angular/core';

@Component({
  standalone: false,
  selector: 'app-main-menu',
  template: `<mat-toolbar color="primary">
  <div class="ui-toolbar-group-left with-icon">
    <button mat-icon-button (click)="toggle()"><mat-icon>menu</mat-icon></button>
    <span class="title"> &nbsp; {{ title }}</span>
  </div>
  <span class="example-fill-remaining-space"></span>
  <app-user-buttons></app-user-buttons>
</mat-toolbar>`,
    styles: []
})
export class MainMenuComponent implements OnInit, OnChanges {
  @Input() dataset: string;
  @Output() emitToggle = new EventEmitter();
  title: string;

  constructor() { }

  ngOnInit() {
    this.init();
  }

  ngOnChanges() {
    this.init();
  }

  private init(): void {
    this.title = 'gedbrowserng - ' + this.dataset;
  }

  toggle() {
    this.emitToggle.emit();
  }
}
