import { Component, OnInit, OnChanges, Input, Output, EventEmitter , Inject } from '@angular/core';
import { MatToolbar } from '@angular/material/toolbar';
import { MatIconButton } from '@angular/material/button';
import { MatIcon } from '@angular/material/icon';
import { UserButtonsComponent } from '../user-buttons/user-buttons.component';

@Component({
    selector: 'app-main-menu',
    templateUrl: './main-menu.component.html',
    styleUrls: ['./main-menu.component.css'],
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
