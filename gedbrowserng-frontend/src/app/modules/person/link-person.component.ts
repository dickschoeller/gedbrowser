import { Component, Input, EventEmitter, Output, Inject } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';

import { LinkPersonDialogComponent } from '../../components';
import { LinkCheck } from '../../interfaces/link-check';
import { LinkPersonDialogData } from '../../models';
import { LinkPersonHelper, PersonService } from '../../services';
import { MatIconButton } from '@angular/material/button';
import { MatTooltip } from '@angular/material/tooltip';
import { MatIcon } from '@angular/material/icon';

@Component({
    selector: 'app-link-person',
    template: `<button (click)="openDialog()" mat-icon-button [color]="color" matTooltip="{{ label }}"><mat-icon>link</mat-icon></button>
<!-- app-link-person-dialog
    [p]="this" [dataset]="dataset"
    [titleString]="label" [multi]="multi"
    (emitClose)="onDialogClose()"
    (emitOpen)="onDialogOpen($event)"
    (emitOK)="onDialogOK($event)"
    [(display)]="displayDialog">
</app-link-person-dialog -->`,
    styles: [],
    imports: [MatIconButton, MatTooltip, MatIcon]
})
export class LinkPersonComponent {
  @Input() dataset: string;
  @Input() parent: LinkCheck;
  @Input() multi: boolean;
  @Input() label: string;
  @Input() color = '';
  @Output() emitOK = new EventEmitter<LinkPersonDialogData>();

  lph: LinkPersonHelper;

  constructor(@Inject(PersonService) @Inject(PersonService) @Inject(PersonService) @Inject(PersonService) private readonly personService: PersonService,
    @Inject(MatDialog) @Inject(MatDialog) @Inject(MatDialog) public readonly dialog: MatDialog) {
        this.lph = new LinkPersonHelper(this.personService);
    }


  openDialog(): void {
    const dialogRef = this.dialog.open(
      LinkPersonDialogComponent,
      {
        data: { dataset: this.dataset, titleString: this.label, multi: this.multi }
      });

    dialogRef.afterOpened().subscribe(() => {
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
