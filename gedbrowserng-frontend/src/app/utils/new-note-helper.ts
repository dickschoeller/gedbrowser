import { ApiNote, NewNoteDialogData } from '../models';

export class NewNoteHelper {
  constructor() {}

  buildNote(data: NewNoteDialogData): ApiNote {
    const note: ApiNote = new ApiNote();
    note.type = 'note';
    note.tail = data.text;
    return note;
  }

  initNew(text: string): NewNoteDialogData {
    return {text: text};
  }
}
