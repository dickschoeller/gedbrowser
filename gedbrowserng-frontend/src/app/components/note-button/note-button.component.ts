import { Component, Input } from '@angular/core';
import { MatDialog } from '@angular/material';

import { HasAttributeList } from '../../interfaces';
import { NoteCreator } from '../../bases';
import { ApiObject, ApiNote, ApiAttribute, LinkDialogData, LinkItem } from '../../models';
import { NoteService, NewNoteLinkService, ServiceBase } from '../../services';
import { UrlBuilder, NewNoteHelper, ApiComparators, LinkHelper, Refresher, DialogHelper } from '../../utils';
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
    public service: NoteService,
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
    DialogHelper.openLinkDialog(this, 'Link Note', this.lh());
  }

  openUnlinkNoteDialog() {
    DialogHelper.openUnlinkDialog(this, 'Unlink Note', this.lh());
  }
}
