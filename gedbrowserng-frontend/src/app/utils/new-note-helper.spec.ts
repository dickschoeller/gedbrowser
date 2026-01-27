import { describe, it, expect } from 'vitest';
import { NewNoteHelper } from './new-note-helper';

describe('NewNoteHelper', () => {
  it('buildNote creates ApiNote from dialog data', () => {
    const data = { text: 'Test note content' };
    const note = NewNoteHelper.buildNote(data);
    expect(note.type).toBe('note');
    expect(note.tail).toBe('Test note content');
  });

  it('buildNote handles empty text', () => {
    const data = { text: '' };
    const note = NewNoteHelper.buildNote(data);
    expect(note.type).toBe('note');
    expect(note.tail).toBe('');
  });

  it('initNew creates dialog data with text', () => {
    const data = NewNoteHelper.initNew('Initial text');
    expect(data.text).toBe('Initial text');
  });

  it('initNew creates dialog data with empty text', () => {
    const data = NewNoteHelper.initNew('');
    expect(data.text).toBe('');
  });
});
