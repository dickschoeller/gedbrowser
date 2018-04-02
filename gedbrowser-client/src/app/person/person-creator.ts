import {MatDialogRef, MatDialog} from '@angular/material';

import {NewPersonDialogData, NewPersonDialogComponent, NewPersonHelper} from '../new-person-dialog';
import {ApiPerson} from '../shared/models';
import {PostRelatedPerson} from '../shared/services/post-related-person';
import { UrlBuilder } from '../shared/services/urlbuilder';

export abstract class PersonCreator {
  nph = new NewPersonHelper();

  constructor(public dialog: MatDialog) {}

  public newPersonDialog2(sex: string, name: string, service: PostRelatedPerson, ub: UrlBuilder): void {
    const dataIn: NewPersonDialogData = this.nph.initialData(sex, name);
    const dialogRef: MatDialogRef<NewPersonDialogComponent> =
      this.dialog.open(NewPersonDialogComponent, this.nph.config(dataIn));
    dialogRef.afterClosed().subscribe(result => this.saveNew2(result, this, service, ub));
  }

  saveNew2(dialogData: NewPersonDialogData, that: PersonCreator, service: PostRelatedPerson, ub: UrlBuilder): void {
    if (that.nph.empty(dialogData)) {
      return;
    }
    const newPerson: ApiPerson = that.nph.buildPerson(dialogData);
    service.p(ub, that.anchor(), newPerson).subscribe(
      (data: ApiPerson) => that.refreshPerson());
  }

  abstract anchor(): string;

  abstract refreshPerson(): void;
}
