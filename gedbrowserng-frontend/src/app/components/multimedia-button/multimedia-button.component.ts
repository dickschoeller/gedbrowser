import { Component, OnInit, Input } from '@angular/core';
import { MenuItem, SelectItem } from 'primeng/api';

import { HasMultimedia } from '../../interfaces';
import { ApiAttribute, MultimediaDialogData, MultimediaFileData } from '../../models';
import { StringUtil, MultimediaDialogHelper } from '../../utils';
import { MultimediaDialogComponent } from '../multimedia-dialog';

@Component({
  selector: 'app-multimedia-button',
  templateUrl: './multimedia-button.component.html',
  styleUrls: ['./multimedia-button.component.css']
})
export class MultimediaButtonComponent implements OnInit {
  @Input() parent: HasMultimedia;
  @Input() dataset: string;

  menuitems: MenuItem[] = [
    {
      label: 'Add multimedia',
      icon: 'fa-plus-circle',
      command: (event: Event) => this.displayDialog = true
    },
  ];
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
}
