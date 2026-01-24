import { Component, OnInit, OnChanges, Input } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { saveAs } from 'file-saver';
import { FileUploadControl, FileUploadValidators } from '@iplab/ngx-file-upload';

import { SaveService, DatasetsService, UploadService, UserService } from '../../services';

@Component({
  standalone: false,
  selector: 'app-side-menu',
  template: `<mat-nav-list>
  <a mat-list-item [routerLink]="['/' + dataset + '/header']"><div class="with-icon"><mat-icon matListIcon>home</mat-icon> Home</div></a>
  <a mat-list-item [routerLink]="['/' + dataset + '/persons']"><div class="with-icon"><mat-icon matListIcon>people</mat-icon> Persons</div></a>
  <a mat-list-item [routerLink]="['/' + dataset + '/notes']"><div class="with-icon"><mat-icon matListIcon>comment</mat-icon> Notes</div></a>
  <a mat-list-item [routerLink]="['/' + dataset + '/sources']"><div class="with-icon"><mat-icon matListIcon>collections_bookmark</mat-icon> Sources</div></a>
  <a mat-list-item [routerLink]="['/' + dataset + '/submitters']"><div class="with-icon"><mat-icon matListIcon>contacts</mat-icon> Submitters</div></a>
  <a mat-list-item (click)="saveFile()" *ngIf="hasSignedIn()"><div class="with-icon"><mat-icon matListIcon>cloud_download</mat-icon> Save</div></a>
  <a mat-list-item [matMenuTriggerFor]="dbPickerMenu"><div class="with-icon"><mat-icon matListIcon>folder_open</mat-icon> Pick dataset</div></a>
  <!-- mat-list-item>
    <wa-mat-file-upload #fileUpload="waMatFileUpload" placeholder="File"
        [multiple]="false" [preview]="true" [selectedText]="selectedText"
        (change)="onFileChange(fileUpload)">Upload GEDCOM file</wa-mat-file-upload>
  </mat-list-item -->
</mat-nav-list>
<form [formGroup]="uploadForm" *ngIf="hasSignedIn()">
  <file-upload formControlName="files">
    <ng-template #placeholder>
      <mat-icon matListIcon class="placeholder">cloud_upload</mat-icon>
      <div class="placeholder">Upload GEDCOM file</div>
    </ng-template>
  </file-upload>
</form>
<mat-menu #dbPickerMenu="matMenu" [overlapTrigger]="false">
  <button *ngFor="let db of dbs" mat-menu-item [routerLink]="['/' + db + '/persons']">{{ db }}</button>
</mat-menu>`,
    styles: []
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
    private userService: UserService,
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
      // Some browsers (and .ged files) don't set a reliable MIME type. Check
      // the file extension first, and fall back to MIME type.
      const name = (value && value.name) ? value.name.toLowerCase() : '';
      const ext = name.includes('.') ? name.substring(name.lastIndexOf('.') + 1) : '';
      const mime = (value && value.type) ? value.type.toLowerCase() : '';
      const isGedExt = (ext === 'ged' || ext === 'gedcom');
      const isGedMime = (mime === 'application/x-gedcom' || mime === 'application/gedcom' || mime === 'text/plain');
      if (!isGedExt && !isGedMime) {
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

    hasSignedIn() {
        return !!this.userService.currentUser;
    }
}