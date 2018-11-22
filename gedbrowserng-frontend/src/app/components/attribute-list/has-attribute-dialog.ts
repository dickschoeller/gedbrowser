import { MatDialog } from '@angular/material';

import { HasAttributeList } from '../../interfaces';
import { ApiAttribute, AttributeDialogData, SelectItem } from '../../models';
import { NewAttributeDialogComponent } from '../attribute-dialog';

export abstract class HasAttributeDialog implements HasAttributeList {
  constructor(public dialog: MatDialog) {}

  openAttributeDialog() {
    return this.dialog.open(
      NewAttributeDialogComponent,
      {
        data: { options: this.options(), data: this.defaultData() }
      });

  }

  abstract get attributes(): Array<ApiAttribute>;
  abstract options(): Array<SelectItem>;
  abstract defaultData(): AttributeDialogData;
  abstract save(): void;
}
