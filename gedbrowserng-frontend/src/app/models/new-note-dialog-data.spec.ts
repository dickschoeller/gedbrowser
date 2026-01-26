import { NewNoteDialogData } from './new-note-dialog-data';

describe('NewNoteDialogData Model', () => {
  it('should create an instance', () => {
    const data = new NewNoteDialogData();
    expect(data).toBeTruthy();
  });

  it('should allow setting text', () => {
    const data = new NewNoteDialogData();
    data.text = 'This is a note';
    expect(data.text).toBe('This is a note');
  });

  it('should handle empty text', () => {
    const data = new NewNoteDialogData();
    data.text = '';
    expect(data.text).toBe('');
  });

  it('should handle multiline text', () => {
    const data = new NewNoteDialogData();
    data.text = 'Line 1\nLine 2\nLine 3';
    expect(data.text).toBe('Line 1\nLine 2\nLine 3');
  });
});
