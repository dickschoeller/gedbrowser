import { Component, Input , Inject } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';

import { HasAttributeList } from '../../interfaces';
import { NoteCreator } from '../../bases';
import { ApiObject, ApiNote, ApiAttribute, LinkDialogData, LinkItem } from '../../models';
import { NoteService } from '../../services';
import { UrlBuilder, NewNoteHelper, ApiComparators, LinkHelper, Refresher, LinkDialogLauncher, UnlinkHelper } from '../../utils';
import { MatIconButton } from '@angular/material/button';
import { MatTooltip } from '@angular/material/tooltip';
import { MatMenuTrigger, MatMenu, MatMenuItem } from '@angular/material/menu';
import { MatIcon } from '@angular/material/icon';

@Component({
    selector: 'app-note-button',
    template: `<span>
  <button mat-icon-button matTooltip="Note" [matMenuTriggerFor]="noteMenu" color="primary">
    <mat-icon matListIcon>comment</mat-icon></button>
</span>

<mat-menu #noteMenu="matMenu" [overlapTrigger]="false">
  <button mat-menu-item (click)="openCreateNoteDialog()"><mat-icon>add_comment</mat-icon> Add note</button>
  <button mat-menu-item (click)="openLinkNoteDialog()"><mat-icon>link</mat-icon> Link note</button>
  <button mat-menu-item (click)="openUnlinkNoteDialog()"><mat-icon color="warn">link_off</mat-icon> Unlink note</button>
</mat-menu>`,
    styles: [],
    imports: [MatIconButton, MatTooltip, MatMenuTrigger, MatIcon, MatMenu, MatMenuItem]
})
export class NoteButtonComponent extends NoteCreator {
  @Input() parent: HasAttributeList;
  @Input() dataset: string;

  constructor(@Inject(NoteService) @Inject(NoteService) @Inject(NoteService) @Inject(NoteService) public readonly service: NoteService, @Inject(MatDialog) @Inject(MatDialog) @Inject(MatDialog) public readonly dialog: MatDialog) {
    super(service, dialog);
  }

  noteUB(): UrlBuilder {
    // This would enable creating a note but not linking.
    return new UrlBuilder(this.dataset, 'notes');
  }

  noteAnchor(): string {
    return undefined;
  }

  refreshNote(note: ApiNote): void {
    Refresher.refresh(this.parent, 'noteLink', note.string);
  }

  openLinkNoteDialog() {
    const lh = new LinkHelper((o: ApiNote) => o.tail, ApiComparators.compareNotes, 'notelink');
    LinkDialogLauncher.openDialog(this, 'Link Note', lh);
  }

  openUnlinkNoteDialog() {
    const lh = new UnlinkHelper((o: ApiNote) => o.tail, ApiComparators.compareNotes, 'notelink');
    LinkDialogLauncher.openDialog(this, 'Unlink Note', lh);
  }
}
