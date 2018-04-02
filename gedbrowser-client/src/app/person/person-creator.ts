import {MatDialogRef, MatDialog} from '@angular/material';

import {NewPersonDialogData, NewPersonDialogComponent, NewPersonHelper} from '../new-person-dialog';
import {ApiPerson} from '../shared/models';
import {PostRelatedPerson} from '../shared/services/post-related-person';

export abstract class PersonCreator {
  nph = new NewPersonHelper();

  constructor(public dialog: MatDialog) {}

  public newPersonDialog1(sex: string, name: string, service: PostRelatedPerson): void {
    const dataIn: NewPersonDialogData = this.nph.initialData(sex, name);
    const dialogRef: MatDialogRef<NewPersonDialogComponent> =
      this.dialog.open(NewPersonDialogComponent, this.nph.config(dataIn));
    dialogRef.afterClosed().subscribe(result => this.saveNew(result, this, service));
  }

  saveNew(dialogData: NewPersonDialogData, that: PersonCreator, service: PostRelatedPerson): void {
    if (that.nph.empty(dialogData)) {
      return;
    }
    const newPerson: ApiPerson = that.nph.buildPerson(dialogData);
    service.post('schoeller', that.anchor(), newPerson).subscribe(
      (data: ApiPerson) => that.refreshPerson());
  }

  abstract anchor(): string;

  abstract refreshPerson(): void;
}
