import { Component, OnInit, OnChanges, Input , Inject } from '@angular/core';
import { FormControl, FormGroup, FormsModule, ReactiveFormsModule } from '@angular/forms';
import { saveAs } from 'file-saver';
import { FileUploadControl, FileUploadValidators, FileUploadComponent } from '@iplab/ngx-file-upload';

import { SaveService, DatasetsService, UploadService, UserService } from '../../services';
import { MatNavList, MatListItem } from '@angular/material/list';
import { RouterLink } from '@angular/router';
import { MatIcon } from '@angular/material/icon';
import { MatMenuTrigger, MatMenu, MatMenuItem } from '@angular/material/menu';

@Component({
    selector: 'app-side-menu',
    template: `<mat-nav-list>
  <a mat-list-item [routerLink]="['/' + dataset + '/header']"><div class="with-icon"><mat-icon inline=true matListIcon>home</mat-icon> Home</div></a>
  <a mat-list-item [routerLink]="['/' + dataset + '/persons']"><div class="with-icon"><mat-icon inline=true matListIcon>people</mat-icon> Persons</div></a>
  <a mat-list-item [routerLink]="['/' + dataset + '/notes']"><div class="with-icon"><mat-icon inline=true matListIcon>comment</mat-icon> Notes</div></a>
  <a mat-list-item [routerLink]="['/' + dataset + '/sources']"><div class="with-icon"><mat-icon inline=true matListIcon>collections_bookmark</mat-icon> Sources</div></a>
  <a mat-list-item [routerLink]="['/' + dataset + '/submitters']"><div class="with-icon"><mat-icon inline=true matListIcon>contacts</mat-icon> Submitters</div></a>
  @if (hasSignedIn()) {
    <a mat-list-item (click)="saveFile()"><div class="with-icon"><mat-icon inline=true matListIcon>cloud_download</mat-icon> Save</div></a>
  }
  <a mat-list-item [matMenuTriggerFor]="dbPickerMenu"><div class="with-icon"><mat-icon inline=true matListIcon>folder_open</mat-icon> Pick dataset</div></a>
  <!-- mat-list-item>
    <wa-mat-file-upload #fileUpload="waMatFileUpload" placeholder="File"
        [multiple]="false" [preview]="true" [selectedText]="selectedText"
        (change)="onFileChange(fileUpload)">Upload GEDCOM file</wa-mat-file-upload>
  </mat-list-item -->
</mat-nav-list>
@if (hasSignedIn()) {
  <form [formGroup]="uploadForm">
    <file-upload formControlName="files">
      <ng-template #placeholder>
        <mat-icon inline=true matListIcon class="placeholder">cloud_upload</mat-icon>
        <div class="placeholder">Upload GEDCOM file</div>
      </ng-template>
    </file-upload>
  </form>
}
<mat-menu #dbPickerMenu="matMenu" [overlapTrigger]="false">
  @for (db of dbs; track $index) {
    <button mat-menu-item [routerLink]="['/' + db + '/persons']">{{ db }}</button>
  }
</mat-menu>`,
    styles: [`
form {
  padding-left: 20px;
  padding-right: 20px;
}

file-upload.has-files div.placeholder {
  display: none;
}

file-upload.has-files mat-icon.placeholder {
  display: none;
}

file-upload {
  outline-color: #000000 !important;
  background-color: #ffffff !important;
}

file-upload.ng-touched.ng-valid {
  outline-color: #000000 !important;
  background-color: #ffffff !important;
}

file-upload.ng-touched.ng-valid.has-files {
  outline-color: #00ff00 !important;
  background-color: #f4fdf4 !important;
}

file-upload.ng-touched.ng-invalid.has-files {
  outline-color: #ff0000 !important;
  background-color: #fdf4f4 !important;
}

.mat-icon {
    vertical-align: top;
    font-size: 1.25em;
}
    `],
    imports: [MatNavList, MatListItem, RouterLink, MatIcon, MatMenuTrigger, FormsModule, ReactiveFormsModule, FileUploadComponent, MatMenu, MatMenuItem]
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
    @Inject(DatasetsService) private readonly datasetService: DatasetsService,
    @Inject(SaveService) private readonly saveService: SaveService,
    @Inject(UploadService) private readonly uploadService: UploadService,
    @Inject(UserService) private readonly userService: UserService,
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
      const name = value?.name?.toLowerCase() ?? '';
      const ext = name.includes('.') ? name.substring(name.lastIndexOf('.') + 1) : '';
      const mime = value?.type?.toLowerCase() ?? '';
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
    for (const db of dbs.toSorted((a, b) => a.localeCompare(b))) {
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