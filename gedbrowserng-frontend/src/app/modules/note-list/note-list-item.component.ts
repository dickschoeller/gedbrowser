import { Component, Input } from '@angular/core';

import { RefreshNote } from '../../interfaces';
import { ApiNote, ApiObject } from '../../models';
import { NoteService } from '../../services';
import { StringUtil } from '../../utils';

@Component({
  selector: 'app-note-list-item',
  templateUrl: './note-list-item.component.html',
  styleUrls: ['./note-list-item.component.css']
})
export class NoteListItemComponent {
  @Input() parent: RefreshNote;
  @Input() dataset: string;
  @Input() note: ApiNote;

  constructor(private noteService: NoteService) {
  }

  delete() {
    this.noteService.delete(this.dataset, this.note).subscribe((note: ApiNote) => {
      this.parent.refreshNote(note);
    });
  }

  truncateNote(length = 80): string {
    if (this.note === undefined) {
      return '';
    }
    return StringUtil.truncate(this.note.tail, length);
  }
}
