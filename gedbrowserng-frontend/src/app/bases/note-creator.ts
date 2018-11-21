import { MatDialog, MatDialogRef, } from '@angular/material';

import { RefreshNote } from '../interfaces/refresh-note';
import { ApiNote, NewNoteDialogData } from '../models';
import { UrlBuilder, NewNoteHelper } from '../utils';
import { NewNoteLinkService } from '../services';
import { NewNoteDialogComponent } from '../components/new-note-dialog';

export abstract class NoteCreator implements RefreshNote {
  data: NewNoteDialogData;

  constructor(public newNoteLinkService: NewNoteLinkService,
    public dialog: MatDialog) {}

  createNote(data: NewNoteDialogData): void {
    if (data != null && data !== undefined) {
      const newNote: ApiNote = NewNoteHelper.buildNote(data);
      this.newNoteLinkService.post(this.noteUB(), this.noteAnchor(), newNote)
        .subscribe((note: ApiNote) => this.refreshNote(note));
    }
  }

  openCreateNoteDialog() {
    const dialogRef = this.dialog.open(
      NewNoteDialogComponent,
      {
        data: { text: 'New Note' }
      });

    dialogRef.afterClosed().subscribe(result => {
      if (result !== undefined) {
        this.data = result;
        this.createNote(this.data);
      }
    });
  }

  abstract noteUB(): UrlBuilder;

  abstract noteAnchor(): string;

  abstract refreshNote(note: ApiNote): void;
}
