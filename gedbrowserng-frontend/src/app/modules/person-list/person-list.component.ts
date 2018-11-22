import { Component, Input } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { MatDialog } from '@angular/material';

import { PersonCreator } from '../../bases';
import { NewPersonDialogComponent } from '../../components';
import { ApiPerson, NewPersonDialogData } from '../../models';
import { PersonService, NewPersonLinkService } from '../../services';
import { UrlBuilder, NewPersonHelper } from '../../utils';
import { PersonListPageComponent } from './person-list-page.component';

@Component({
  selector: 'app-person-list',
  templateUrl: './person-list.component.html',
  styleUrls: ['./person-list.component.css']
})
export class PersonListComponent extends PersonCreator {
  @Input() p: PersonListPageComponent;
  @Input() dataset: string;
  @Input() persons: ApiPerson[];

  constructor(
    public newPersonLinkService: NewPersonLinkService,
    public dialog: MatDialog
  ) {
    super(newPersonLinkService);
  }

  openCreatePersonDialog(): void {
    const dialogRef = this.dialog.open(
      NewPersonDialogComponent,
      {
        data: NewPersonHelper.initNew('M', '')
      });

    dialogRef.afterClosed().subscribe((result: NewPersonDialogData) => {
      if (result !== undefined) {
        this.createPerson(result);
      }
    });
  }

  personUB(): UrlBuilder {
    return new UrlBuilder(this.dataset, 'persons');
  }

  personAnchor(): string {
    return undefined;
  }

  refreshPerson() {
    this.p.refreshPerson();
  }
}
