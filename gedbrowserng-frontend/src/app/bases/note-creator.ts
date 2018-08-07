import { RefreshNote } from '../interfaces/refresh-note';
import { ApiNote, NewNoteDialogData } from '../models';
import { UrlBuilder, NewNoteHelper } from '../utils';
import { NewNoteLinkService } from '../services';

export abstract class NoteCreator implements RefreshNote {
  constructor(public newNoteLinkService: NewNoteLinkService) {}

  createNote(data: NewNoteDialogData): void {
    if (data != null && data !== undefined) {
      const newNote: ApiNote = NewNoteHelper.buildNote(data);
      this.newNoteLinkService.post(this.noteUB(), this.noteAnchor(), newNote)
        .subscribe((note: ApiNote) => this.refreshNote(note));
    }
  }

  abstract closeNoteDialog(): void;

  abstract noteUB(): UrlBuilder;

  abstract noteAnchor(): string;

  abstract refreshNote(note: ApiNote): void;
}
