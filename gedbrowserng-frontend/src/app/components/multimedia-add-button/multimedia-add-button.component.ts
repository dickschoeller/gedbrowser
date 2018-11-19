import { Component, OnInit, Input } from '@angular/core';
import { MenuItem, SelectItem } from 'primeng/api';

import { HasMultimedia } from '../../interfaces';
import { ApiAttribute, MultimediaDialogData, MultimediaFileData } from '../../models';
import { StringUtil, MultimediaDialogHelper } from '../../utils';
import { MultimediaDialogComponent } from '../multimedia-dialog';

@Component({
  selector: 'app-multimedia-add-button',
  templateUrl: './multimedia-add-button.component.html',
  styleUrls: ['./multimedia-add-button.component.css']
})
export class MultimediaAddButtonComponent implements OnInit {
  @Input() parent: HasMultimedia;
  @Input() dataset: string;

  displayDialog = false;

  constructor() { }

  ngOnInit() {
  }

  onDialogClose() {
    this.displayDialog = false;
  }

  onDialogOpen(data: MultimediaDialogComponent) {
    if (data !== undefined) {
      data._data = { title: 'Title', files: [ { fileUrl: '' } ] };
    }
  }

  create(data: MultimediaDialogData) {
    const attribute: ApiAttribute = MultimediaDialogHelper.buildMultimediaAttribute(data);
    this.parent.multimedia.push(attribute);
    this.parent.save();
  }

  openMultimediaDialog() {
    this.displayDialog = true;
  }
}
