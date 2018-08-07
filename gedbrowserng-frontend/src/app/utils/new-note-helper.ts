import { ApiNote, NewNoteDialogData } from '../models';

export class NewNoteHelper {
  public static buildNote(data: NewNoteDialogData): ApiNote {
    const note: ApiNote = new ApiNote();
    note.type = 'note';
    note.tail = data.text;
    return note;
  }

  public static initNew(text: string): NewNoteDialogData {
    return {text: text};
  }
}
