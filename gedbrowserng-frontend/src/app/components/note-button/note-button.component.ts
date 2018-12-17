import { Component, Input } from '@angular/core';
import { MatDialog } from '@angular/material';

import { HasAttributeList } from '../../interfaces';
import { NoteCreator } from '../../bases';
import { ApiObject, ApiNote, ApiAttribute, LinkDialogData, LinkItem } from '../../models';
import { NoteService, NewNoteLinkService, ServiceBase } from '../../services';
import { UrlBuilder, NewNoteHelper, ApiComparators, LinkHelper, Refresher } from '../../utils';
import { LinkDialogComponent } from '../link-dialog';

@Component({
  selector: 'app-note-button',
  templateUrl: './note-button.component.html',
  styleUrls: ['./note-button.component.css']
})
export class NoteButtonComponent extends NoteCreator {
  @Input() parent: HasAttributeList;
  @Input() dataset: string;

  constructor(
    public noteService: NoteService,
    public newNoteLinkService: NewNoteLinkService,
    public dialog: MatDialog,
  ) {
    super(newNoteLinkService, dialog);
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

  lh(): LinkHelper {
    return new LinkHelper((o: ApiNote) => o.tail, ApiComparators.compareNotes, 'notelink');
  }

  openLinkNoteDialog() {
    const dialogRef = this.dialog.open(
      LinkDialogComponent,
      {
        data: { name: 'Link Note' }
      });

    dialogRef.afterOpen().subscribe(() => {
      this.lh().onLinkDialogOpen(this.dataset, this.noteService, dialogRef.componentInstance);
    });

    dialogRef.afterClosed().subscribe((result: LinkDialogData) => {
      if (result !== undefined) {
         this.lh().link(result, this.parent.attributes, () => this.parent.save());
      }
    });
  }

  openUnlinkNoteDialog() {
    const dialogRef = this.dialog.open(
      LinkDialogComponent,
      {
        data: { name: 'Unlink Note' }
      });

    dialogRef.afterOpen().subscribe(() => {
      this.lh().onUnlinkDialogOpen(this.dataset, this.noteService, dialogRef.componentInstance, this.parent.attributes);
    });

    dialogRef.afterClosed().subscribe((result: LinkDialogData) => {
      if (result !== undefined) {
         this.lh().unlink(result, this.parent.attributes, () => this.parent.save());
      }
    });
  }
}
