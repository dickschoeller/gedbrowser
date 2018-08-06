import { Component, Input } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NoteCreator } from '../../bases';
import { NewNoteDialogComponent } from '../../components';
import { RefreshNote } from '../../interfaces';
import { ApiNote, NewPersonDialogData } from '../../models';
import { NoteService, NewNoteLinkService } from '../../services';
import { NewNoteHelper, UrlBuilder } from '../../utils';
import { NoteListPageComponent } from './note-list-page.component';

@Component({
  selector: 'app-note-list',
  templateUrl: './note-list.component.html',
  styleUrls: ['./note-list.component.css']
})
export class NoteListComponent extends NoteCreator implements RefreshNote {
  @Input() parent: RefreshNote;
  @Input() dataset: string;
  @Input() notes: Array<ApiNote>;
  displayNoteDialog = false;

  constructor(public newNoteLinkService: NewNoteLinkService) {
    super(newNoteLinkService);
  }

  noteUB(): UrlBuilder {
    return new UrlBuilder(this.dataset, 'notes');
  }

  openCreateNoteDialog(): void {
    this.displayNoteDialog = true;
  }

  closeNoteDialog(): void {
    this.displayNoteDialog = false;
  }

  onDialogOpen(data: NewNoteDialogComponent) {
    data._data = this.nnh.initNew('New Note');
  }

  noteAnchor(): string {
    return undefined;
  }

  refreshNote(note: ApiNote) {
    this.parent.refreshNote(note);
  }
}
