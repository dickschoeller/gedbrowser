import { Component } from '@angular/core';
import { saveAs } from 'file-saver/FileSaver';

import { SaveService } from './services';

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
