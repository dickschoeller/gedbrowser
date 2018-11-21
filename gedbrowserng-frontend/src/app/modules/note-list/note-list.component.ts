import { Component, Input } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { MatDialog, MatDialogRef, } from '@angular/material';

import { NoteCreator } from '../../bases';
import { NewNoteDialogComponent } from '../../components';
import { NewNoteDialogData } from '../../models';
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

  data: NewNoteDialogData;

  constructor(public newNoteLinkService: NewNoteLinkService,
    public dialog: MatDialog,
  ) {
    super(newNoteLinkService);
  }

  noteUB(): UrlBuilder {
    return new UrlBuilder(this.dataset, 'notes');
  }

  openCreateNoteDialog(): void {
    const dialogRef = this.dialog.open(
      NewNoteDialogComponent,
      {
        data: { title: 'New Source', abbreviation: 'NewSource', text: '' }
      });

    dialogRef.afterClosed().subscribe(result => {
      if (result !== undefined) {
        this.data = result;
        this.createNote(this.data);
      }
    });
  }

  noteAnchor(): string {
    return undefined;
  }

  refreshNote(note: ApiNote) {
    this.parent.refreshNote(note);
  }
}
