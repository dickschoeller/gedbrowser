import { Component, Input, EventEmitter, Output , Inject } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';

import { NewPersonDialogComponent } from '../../components';
import { NewPersonDialogData } from '../../models';
import { NewPersonHelper } from '../../utils';
import { MatIconButton } from '@angular/material/button';
import { MatTooltip } from '@angular/material/tooltip';
import { MatIcon } from '@angular/material/icon';

@Component({
    selector: 'app-new-person',
    template: `<button (click)="openDialog()" mat-icon-button [color]="color"
    matTooltip="{{ label }}"><mat-icon>person_add</mat-icon></button>`,
    styles: [],
    imports: [MatIconButton, MatTooltip, MatIcon]
})
export class NewPersonComponent {
  @Input() sex: string;
  @Input() surname: string;
  @Input() label: string;
  @Input() color = '';
  @Output() emitOK = new EventEmitter<NewPersonDialogData>();

  nph = new NewPersonHelper();

  constructor(@Inject(MatDialog) @Inject(MatDialog) @Inject(MatDialog) @Inject(MatDialog) public readonly dialog: MatDialog) {
  }

  openDialog(): void {
    const dialogRef = this.dialog.open(
      NewPersonDialogComponent,
      {
        width: '72rem',
        maxWidth: '95vw',
        data: NewPersonHelper.initNew(this.sex, this.surname)
      });

    dialogRef.afterClosed().subscribe((result: NewPersonDialogData) => {
      if (result !== undefined) {
        this.emitOK.emit(result);
      }
    });
  }
}
