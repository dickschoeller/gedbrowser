import { describe, it, expect } from 'vitest';
import { of } from 'rxjs';

import { NoteCreator } from './note-creator';
import { UrlBuilder } from '../utils';
import { ApiNote, NewNoteDialogData } from '../models';

class StubNoteService {
  posted = false;
  postLink(ub: UrlBuilder, anchor: string, note: ApiNote) {
    this.posted = true;
    return of(new ApiNote());
  }
}

class StubDialogRef<T> {
  constructor(private value: T | undefined) {}
  afterClosed() { return of(this.value); }
}

class StubDialog {
  result: any;
  open(_: any, __: any) { return new StubDialogRef(this.result); }
}

class TestNoteCreator extends NoteCreator {
  refreshed: ApiNote | null = null;
  constructor(public noteService: StubNoteService, public dialog: StubDialog) { super(noteService as any, dialog as any); }
  noteUB(): UrlBuilder { return new UrlBuilder('ds', 'notes'); }
  noteAnchor(): string { return 'N1'; }
  refreshNote(note: ApiNote): void { this.refreshed = note; }
}

describe('NoteCreator', () => {
  it('createNote no-ops on null/undefined', () => {
    const svc = new StubNoteService();
    const dlg = new StubDialog();
    const creator = new TestNoteCreator(svc, dlg);
    creator.createNote(null as unknown as NewNoteDialogData);
    expect(svc.posted).toBe(false);
    creator.createNote(undefined as unknown as NewNoteDialogData);
    expect(svc.posted).toBe(false);
  });

  it('createNote posts and refreshes on valid data', () => {
    const svc = new StubNoteService();
    const dlg = new StubDialog();
    const creator = new TestNoteCreator(svc, dlg);
    creator.createNote({ text: 'Hello' });
    expect(svc.posted).toBe(true);
    expect(creator.refreshed).not.toBeNull();
  });

  it('openCreateNoteDialog handles undefined result and valid result', () => {
    const svc = new StubNoteService();
    const dlg = new StubDialog();
    const creator = new TestNoteCreator(svc, dlg);

    // undefined branch: should not post
    dlg.result = undefined;
    creator.openCreateNoteDialog();
    expect(svc.posted).toBe(false);

    // defined branch: should call createNote and post
    dlg.result = { text: 'New Note' };
    creator.openCreateNoteDialog();
    expect(svc.posted).toBe(true);
  });
});
