import { Component, Input, EventEmitter, Output, } from '@angular/core';
import { MatDialog } from '@angular/material';

import { LinkPersonDialogComponent } from '../../components';
import { LinkCheck } from '../../interfaces/link-check';
import { LinkPersonDialogData } from '../../models';
import { LinkPersonHelper, PersonService } from '../../services';

@Component({
  selector: 'app-link-person',
  templateUrl: './link-person.component.html',
  styleUrls: ['./link-person.component.css']
})
export class LinkPersonComponent {
  @Input() dataset: string;
  @Input() parent: LinkCheck;
  @Input() multi: boolean;
  @Input() label: string;
  @Input() color = '';
  @Output() emitOK = new EventEmitter<LinkPersonDialogData>();

  constructor(private personService: PersonService,
    public dialog: MatDialog) { }

  lph = new LinkPersonHelper(this.personService);

  openDialog(): void {
    const dialogRef = this.dialog.open(
      LinkPersonDialogComponent,
      {
        data: { dataset: this.dataset, titleString: this.label, multi: this.multi }
      });

    dialogRef.afterOpen().subscribe(() => {
      this.dataset = dialogRef.componentInstance.data.dataset;
      this.lph.onLinkChildDialogOpen(dialogRef.componentInstance, this.parent);
    });

    dialogRef.afterClosed().subscribe((result: LinkPersonDialogData) => {
      if (result !== undefined) {
        this.emitOK.emit(result);
      }
    });
  }
}
