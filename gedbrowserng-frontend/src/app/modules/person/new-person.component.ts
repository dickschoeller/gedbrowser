import { Component, Input, EventEmitter, Output, } from '@angular/core';

import { NewPersonDialogComponent } from '../../components';
import { NewPersonDialogData } from '../../models';
import { NewPersonHelper } from '../../utils';

@Component({
  selector: 'app-new-person',
  templateUrl: './new-person.component.html',
  styleUrls: ['./new-person.component.css']
})
export class NewPersonComponent {
  @Input() sex: string;
  @Input() surname: string;
  @Input() label: string;
  @Output() emitOK = new EventEmitter<NewPersonDialogData>();

  displayDialog = false;
  nph = new NewPersonHelper();

  constructor() { }

  openDialog(): void {
    this.displayDialog = true;
  }

  onDialogClose(): void {
    this.displayDialog = false;
  }

  onDialogOpen(data: NewPersonDialogComponent): void {
    data._data = this.nph.initNew(this.sex, this.surname);
  }

  onDialogOK(data: NewPersonDialogData): void {
    this.displayDialog = false;
    this.emitOK.emit(data);
  }
}
