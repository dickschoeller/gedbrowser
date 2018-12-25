import { Component, OnInit, OnChanges, Input } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { saveAs } from 'file-saver';
import { FileUploadControl, FileUploadValidators } from '@iplab/ngx-file-upload';

import { SaveService, DatasetsService, UploadService } from '../../services';

@Component({
  selector: 'app-side-menu',
  templateUrl: './side-menu.component.html',
  styleUrls: ['./side-menu.component.css']
})
export class SideMenuComponent implements OnInit, OnChanges {
  @Input() dataset: string;
  title: string;
  dbs: Array<string> = new Array<string>();
  public fileUploadControl = new FileUploadControl();
  public readonly filesControl = new FormControl(
    null,
    [
      FileUploadValidators.accept(['.ged']),
      FileUploadValidators.filesLimit(1)
    ]
  );
  public readonly uploadForm = new FormGroup({ files: this.filesControl });

  constructor(
    private datasetService: DatasetsService,
    private saveService: SaveService,
    private uploadService: UploadService,
  ) { }

  ngOnInit() {
    this.init();
    this.initFileUpload();
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

  private initFileUpload(): void {
    this.fileUploadControl.setListVisibility(true);
    this.filesControl.valueChanges.subscribe((values: File[]) => {
      if (values.length === 0) {
        return;
      }
      const value: File = values.shift();
      if (value.type !== 'ged' && value.type !== 'application/x-gedcom') {
        alert('won\'t upload ' + value.name + '. unsupported file type.');
        this.filesControl.setValue(values);
        return;
      }
      this.uploadService.uploadGedFile(value).subscribe(
        (result) => {
          this.filesControl.setValue(values);
          this.init();
        },
        (error) => {
          alert('Error, unable to upload ' + value.name);
          this.filesControl.setValue(values);
        }
      );
    });
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
