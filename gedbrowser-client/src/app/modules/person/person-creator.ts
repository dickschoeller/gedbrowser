import {MatDialogRef, MatDialog} from '@angular/material';

import {NewPersonDialogData, NewPersonDialogComponent, NewPersonHelper} from '../../components/new-person-dialog';
import {ApiPerson} from '../../models';
import {PostRelatedPerson, UrlBuilder} from '../../services';

export abstract class PersonCreator {
  nph = new NewPersonHelper();

  constructor(public dialog: MatDialog) {}

  public newPersonDialog2(sex: string, name: string, service: PostRelatedPerson, ub: UrlBuilder): void {
    const dataIn: NewPersonDialogData = this.nph.initialData(sex, name);
    const dialogRef: MatDialogRef<NewPersonDialogComponent> =
      this.dialog.open(NewPersonDialogComponent, this.nph.config(dataIn));

    const sub =
      dialogRef.componentInstance.onOK.subscribe(
        result => this.saveNewWrapper(this, result, service, ub));

    dialogRef.afterClosed().subscribe(() => { sub.unsubscribe(); });
  }

  saveNewWrapper(that: PersonCreator,
    dialogData: NewPersonDialogData, service: PostRelatedPerson,
    ub: UrlBuilder): void {
    that.saveNew(dialogData, service, ub);
  }

  saveNew(dialogData: NewPersonDialogData, service: PostRelatedPerson,
    ub: UrlBuilder): void {
    const newPerson: ApiPerson = this.nph.buildPerson(dialogData);
    service.p(ub, this.anchor(), newPerson).subscribe(
      (data: ApiPerson) => this.refreshPerson());
  }

  abstract anchor(): string;

  abstract refreshPerson(): void;
}
