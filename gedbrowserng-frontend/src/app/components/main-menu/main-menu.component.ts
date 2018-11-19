import { Component, OnInit, OnChanges, Input, Output, EventEmitter } from '@angular/core';
import { saveAs } from 'file-saver/FileSaver';
import { MenuItem } from 'primeng/api';

import { SaveService, DatasetsService } from '../../services';

@Component({
  selector: 'app-main-menu',
  templateUrl: './main-menu.component.html',
  styleUrls: ['./main-menu.component.css']
})
export class MainMenuComponent implements OnInit, OnChanges {
  @Input() dataset: string;
  @Output() emitToggle = new EventEmitter();
  title: string;
  items: MenuItem[];

  constructor(
    private datasetService: DatasetsService,
    private saveService: SaveService) { }

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
