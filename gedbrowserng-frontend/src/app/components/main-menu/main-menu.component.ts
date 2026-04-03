import { Component, OnInit, OnChanges, Input, Output, EventEmitter } from '@angular/core';
import { MatToolbar } from '@angular/material/toolbar';
import { MatIconButton } from '@angular/material/button';
import { MatIcon } from '@angular/material/icon';
import { UserButtonsComponent } from '../user-buttons/user-buttons.component';

@Component({
    selector: 'app-main-menu',
  template: `<mat-toolbar class="custom-toolbar-colors">
  <div class="ui-toolbar-group-left with-icon custom-toolbar-colors">
  <button mat-icon-button (click)="toggle()"><mat-icon>menu</mat-icon></button>
  <span class="title">{{ title }}</span>
  </div>
  <span class="example-fill-remaining-space"></span>
  <app-user-buttons></app-user-buttons>
</mat-toolbar>`,
  styles: [`
.with-icon {
  display: flex;
  align-items: center;
}

.title {
  margin-left: 8px;
  align-self: flex-end;
  line-height: 1;
  padding-bottom: 14px;
}
`],
    imports: [MatToolbar, MatIconButton, MatIcon, UserButtonsComponent]
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
