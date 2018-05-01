import {Component, OnChanges, OnInit, Input} from '@angular/core';
import {HttpResponse} from '@angular/common/http';
import {saveAs} from 'file-saver/FileSaver';
import {MenuItem} from 'primeng/api';

import {SaveService} from './services';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: [
    './app.component.css'
  ]
})
export class AppComponent implements OnInit, OnChanges {
  dataset = 'schoeller';
  title = 'gedbrowser - ' + this.dataset;
  items: MenuItem[];

  constructor(private saveService: SaveService) { }

  ngOnInit() {
    this.init();
  }

  ngOnChanges() {
    this.init();
  }

  private init() {
    this.items = [
      {
        label: 'Persons', icon: 'fa-users', routerLink: ['/' + this.dataset + '/persons']
      },
      {
        label: 'Sources', icon: 'fa-book', routerLink: ['/' + this.dataset + '/sources']
      },
      {
        label: 'Submitters', icon: 'fa-user', routerLink: ['/' + this.dataset + '/submitters']
      },
      {
        label: 'Save', icon: 'fa-save', command: (event: Event) => { this.saveFile(); }
      },
      {
        label: 'Pick dataset', items: [
          {
            'label': 'schoeller', 'command': (event) => { this.pickDataset('schoeller'); },
            routerLink: ['/schoeller/persons']
          },
          {
            'label': 'mini-schoeller', 'command': (event) => { this.pickDataset('mini-schoeller'); },
            routerLink: ['/mini-schoeller/persons']
          },
          {
            'label': 'gl120368', 'command': (event) => { this.pickDataset('gl120368'); },
            routerLink: ['/gl120368/persons']
          }
        ]
      },
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
