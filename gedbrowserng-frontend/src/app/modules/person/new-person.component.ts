import { Component, Input, EventEmitter, Output } from '@angular/core';
import { MatDialog } from '@angular/material';

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
  @Input() color = '';
  @Output() emitOK = new EventEmitter<NewPersonDialogData>();

  nph = new NewPersonHelper();

  constructor(public dialog: MatDialog) {
  }

  openDialog(): void {
    const dialogRef = this.dialog.open(
      NewPersonDialogComponent,
      {
        data: NewPersonHelper.initNew(this.sex, this.surname)
      });

    dialogRef.afterClosed().subscribe((result: NewPersonDialogData) => {
      if (result !== undefined) {
        this.emitOK.emit(result);
      }
    });
  }
}
