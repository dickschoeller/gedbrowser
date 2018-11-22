import { MatDialog } from '@angular/material';

import { HasAttributeList } from '../../interfaces';
import { ApiAttribute, AttributeDialogData, SelectItem } from '../../models';
import { NewAttributeDialogComponent } from '../attribute-dialog';

export abstract class HasAttributeDialog implements HasAttributeList {
  constructor(public dialog: MatDialog) { }

  openAttributeDialog(callback: (result: AttributeDialogData) => void): void {
    const dialogRef = this.dialog.open(
      NewAttributeDialogComponent,
      {
        data: { options: this.options(), data: this.defaultData() }
      });

    dialogRef.afterClosed().subscribe(result => {
      if (result !== undefined) {
        callback(result);
      }
    });
  }

  abstract get attributes(): Array<ApiAttribute>;
  abstract options(): Array<SelectItem>;
  abstract defaultData(): AttributeDialogData;
  abstract save(): void;
}
