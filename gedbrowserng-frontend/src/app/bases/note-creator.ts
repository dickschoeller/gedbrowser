import { RefreshNote } from '../interfaces/refresh-note';
import { ApiNote, NewNoteDialogData } from '../models';
import { NewNoteHelper, UrlBuilder } from '../utils';
import { NewNoteLinkService } from '../services';

export abstract class NoteCreator implements RefreshNote {
  nnh = new NewNoteHelper();

  constructor(public newNoteLinkService: NewNoteLinkService) {}

  createNote(data: NewNoteDialogData): void {
    if (data != null && data !== undefined) {
      const newNote: ApiNote = this.nnh.buildNote(data);
      this.newNoteLinkService.post(this.noteUB(), this.noteAnchor(), newNote)
        .subscribe((note: ApiNote) => this.refreshNote(note));
    }
  }

  abstract closeNoteDialog(): void;

  abstract noteUB(): UrlBuilder;

  abstract noteAnchor(): string;

  abstract refreshNote(note: ApiNote): void;
}
