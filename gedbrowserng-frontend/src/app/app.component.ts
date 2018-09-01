import { Component } from '@angular/core';
import { saveAs } from 'file-saver/FileSaver';
import { MenuItem } from 'primeng/api';

import {SaveService} from './services';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: [
    './app.component.css'
  ]
})
export class AppComponent {
  constructor() { }
}
