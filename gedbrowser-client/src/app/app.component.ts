import {HttpResponse} from '@angular/common/http';
import {Component} from '@angular/core';
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
export class AppComponent {
  title = 'gedbrowser';
  items: MenuItem[] = [
    {
      label: 'Persons', icon: 'fa-users', routerLink: ['/persons']
    },
    {
      label: 'Sources', icon: 'fa-book', routerLink: ['/sources']
    },
    {
      label: 'Submitters', icon: 'fa-user', routerLink: ['/submitters']
    },
    {
      label: 'Save', icon: 'fa-save', command: (event: Event) => { this.saveFile(); }
    }
  ];
  constructor(private saveService: SaveService) { }

  saveFile() {
    this.saveService.getTextFile('schoeller').subscribe(
      results => this.saveToFileSystem(results)
    );
  }

  private saveToFileSystem(response) {
    const blob = new Blob([response], {type: 'text/plain'});
    saveAs(blob, 'schoeller.ged');
  }
}
