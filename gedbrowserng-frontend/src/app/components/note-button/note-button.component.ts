import { Component, Input } from '@angular/core';
import { MatDialog } from '@angular/material';

import { HasAttributeList } from '../../interfaces';
import { NoteCreator } from '../../bases';
import { ApiObject, ApiNote, ApiAttribute, LinkDialogData, LinkItem } from '../../models';
import { NoteService } from '../../services';
import { UrlBuilder, NewNoteHelper, ApiComparators, LinkHelper, Refresher, LinkDialogLauncher, UnlinkHelper } from '../../utils';

@Component({
  selector: 'app-note-button',
  templateUrl: './note-button.component.html',
  styleUrls: ['./note-button.component.css']
})
export class NoteButtonComponent extends NoteCreator {
  @Input() parent: HasAttributeList;
  @Input() dataset: string;

  constructor(public service: NoteService, public dialog: MatDialog) {
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
