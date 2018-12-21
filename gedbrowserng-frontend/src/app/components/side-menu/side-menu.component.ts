import { Component, OnInit, OnChanges, Input } from '@angular/core';
import { saveAs } from 'file-saver';

import { SaveService, DatasetsService } from '../../services';

@Component({
  selector: 'app-side-menu',
  templateUrl: './side-menu.component.html',
  styleUrls: ['./side-menu.component.css']
})
export class SideMenuComponent implements OnInit, OnChanges {
  @Input() dataset: string;
  title: string;
  dbs: Array<string> = new Array<string>();

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
    this.datasetService.get().subscribe(
      (results: Array<string>) => { this.setupItems(results); }
    );
    this.title = 'gedbrowserng - ' + this.dataset;
  }

  setupItems(dbs: Array<string>): void {
    this.dbs = new Array<string>();
    for (const db of dbs.sort()) {
      this.dbs.push(db);
    }
  }

  pickDataset(ds: string) {
    this.dataset = ds;
    this.title = 'gedbrowser - ' + ds;
    this.init();
  }

  saveFile() {
    this.saveService.getTextFile(this.dataset).subscribe(
      results => this.saveToFileSystem(results)
    );
  }

  private saveToFileSystem(response) {
    const blob = new Blob([response], {type: 'text/plain'});
    saveAs(blob, this.dataset + '.ged');
  }

}
