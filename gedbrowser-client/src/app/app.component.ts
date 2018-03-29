import {HttpResponse} from '@angular/common/http';
import {Component} from '@angular/core';
import {saveAs} from 'file-saver/FileSaver';
import {SaveService} from './shared/services';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'gedbrowser';

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
