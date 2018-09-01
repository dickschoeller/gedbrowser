import { Component, OnInit, OnChanges, Input } from '@angular/core';
import {saveAs} from 'file-saver/FileSaver';
import { MenuItem } from 'primeng/api';

import { SaveService, DatasetsService } from '../../services';

@Component({
  selector: 'app-main-menu',
  templateUrl: './main-menu.component.html',
  styleUrls: ['./main-menu.component.css']
})
export class MainMenuComponent implements OnInit, OnChanges {
  @Input() dataset: string;
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
    this.datasetService.get().subscribe(
      (results: Array<string>) => { this.setupItems(results); }
    );
    this.title = 'gedbrowserng - ' + this.dataset;
  }

  setupItems(dbs: Array<string>): void {
    const dbItems: Array<MenuItem> = new Array<MenuItem>();
    for (const db of dbs.sort()) {
      dbItems.push(
        {
          'label': db,
          'command': (event) => { this.pickDataset(db); },
          routerLink: ['/' + db + '/persons']
        });
    }
    this.items = [
      { label: 'Home', icon: 'fa-home', routerLink: ['/' + this.dataset + '/head'] },
      { label: 'Persons', icon: 'fa-users', routerLink: ['/' + this.dataset + '/persons'] },
      { label: 'Sources', icon: 'fa-book', routerLink: ['/' + this.dataset + '/sources'] },
      { label: 'Submitters', icon: 'fa-user', routerLink: ['/' + this.dataset + '/submitters'] },
      { label: 'Notes', icon: 'fw fa-comment', routerLink: ['/' + this.dataset + '/notes'] },
      { label: 'Save', icon: 'fa-save', command: (event: Event) => { this.saveFile(); } },
      { label: 'Pick dataset', items: dbItems },
    ];
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
