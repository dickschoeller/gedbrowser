import { NoteCreator } from '../../bases';
import { Component, Input } from '@angular/core';
import { MenuItem, SelectItem } from 'primeng/api';

import { HasAttributeList } from '../../interfaces';
import { ApiObject, ApiNote, ApiAttribute, LinkDialogData, LinkItem } from '../../models';
import { NoteService, NewNoteLinkService, ServiceBase } from '../../services';
import { UrlBuilder, NewNoteHelper, ApiComparators, LinkHelper, Refresher } from '../../utils';
import { LinkDialogComponent } from '../link-dialog';
import { NewNoteDialogComponent } from '../new-note-dialog';

@Component({
  selector: 'app-note-button',
  templateUrl: './note-button.component.html',
  styleUrls: ['./note-button.component.css']
})
export class NoteButtonComponent extends NoteCreator {
  @Input() parent: HasAttributeList;
  @Input() dataset: string;

  displayNoteDialog = false;
  displayLinkNoteDialog = false;
  displayUnlinkNoteDialog = false;

  constructor(
    public noteService: NoteService,
    public newNoteLinkService: NewNoteLinkService,
  ) {
    super(newNoteLinkService);
  }

  noteUB(): UrlBuilder {
    // This would enable creating a note but not linking.
    return new UrlBuilder(this.dataset, 'notes');
  }

  noteAnchor(): string {
    return undefined;
  }

  closeNoteDialog(): void {
    this.displayNoteDialog = false;
  }

  refreshNote(note: ApiNote): void {
    Refresher.refresh(this.parent, 'noteLink', note.string);
  }

  onNoteDialogClose() {
    this.displayNoteDialog = false;
  }

  onNoteDialogOpen(data: NewNoteDialogComponent) {
    if (data !== undefined) {
      data._data = NewNoteHelper.initNew('New Note');
    }
  }

  onLinkNoteDialogClose() {
    this.displayLinkNoteDialog = false;
  }

  onLinkNoteDialogOpen(dialog: LinkDialogComponent) {
    this.lh().onLinkDialogOpen(this.noteService, dialog);
  }

  linkNote(data: LinkDialogData) {
    this.lh().link(data, this.parent.attributes, () => this.parent.save());
  }

  onUnlinkNoteDialogClose() {
    this.displayUnlinkNoteDialog = false;
  }

  onUnlinkNoteDialogOpen(dialog: LinkDialogComponent) {
    this.lh().onUnlinkDialogOpen(this.noteService, dialog, this.parent.attributes);
  }

  unlinkNote(data: LinkDialogData) {
    this.lh().unlink(data, this.parent.attributes, () => this.parent.save());
  }

  lh(): LinkHelper {
    return new LinkHelper((o: ApiNote) => o.tail, ApiComparators.compareNotes, 'notelink');
  }

  openNoteDialog() {
    this.displayNoteDialog = true;
  }

  openLinkNoteDialog() {
    this.displayLinkNoteDialog = true;
  }

  openUnlinkNoteDialog() {
    this.displayUnlinkNoteDialog = true;
  }
}
