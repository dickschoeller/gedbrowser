import { Component, Input } from '@angular/core';

import { Saveable } from '../../interfaces';
import { ApiAttribute, MultimediaDialogData } from '../../models';
import { MultimediaDialogHelper } from '../../utils';
import { MultimediaDialogComponent } from '../multimedia-dialog';

@Component({
  selector: 'app-multimedia-edit-button',
  templateUrl: './multimedia-edit-button.component.html',
  styleUrls: ['./multimedia-edit-button.component.css']
})
export class MultimediaEditButtonComponent {
  @Input() dataset: string;
  @Input() parent: Saveable;
  @Input() attributes: Array<ApiAttribute>;
  @Input() index;
  displayDialog = false;

  constructor() { }

  edit(): void {
    this.displayDialog = true;
  }

  onDialogOpen(data: MultimediaDialogComponent) {
    if (data !== undefined) {
      data._data = MultimediaDialogHelper.buildMultimediaDialogData(this.attributes, this.index);
    }
  }

  onDialogClose(): void {
    this.displayDialog = false;
  }

  update(data: MultimediaDialogData): void {
    this.attributes.splice(this.index, 1, MultimediaDialogHelper.buildMultimediaAttribute(data));
    this.parent.save();
  }
}
