import { ApiNote } from '../models';
import { vi } from 'vitest';

describe('RefreshNote Interface', () => {
  it('should define refreshNote method', () => {
    const impl: any = {
      refreshNote: (note: ApiNote) => {}
    };
    expect(typeof impl.refreshNote).toBe('function');
  });

  it('should accept ApiNote parameter', () => {
    const impl: any = {
      refreshNote: vi.fn()
    };
    const note = new ApiNote();
    note.string = 'N001';
    impl.refreshNote(note);
    expect(impl.refreshNote).toHaveBeenCalledWith(note);
  });

  it('should support implementations that update state', () => {
    const impl: any = {
      currentNote: null,
      refreshNote: function(note: ApiNote) {
        this.currentNote = note;
      }
    };
    const note = new ApiNote();
    note.string = 'N001';
    impl.refreshNote(note);
    expect(impl.currentNote).toBe(note);
  });
});
