import { ApiNote } from './api-note.model';

describe('ApiNote Model', () => {
  it('should create an ApiNote instance', () => {
    const note = new ApiNote();
    expect(note).toBeTruthy();
  });

  it('should inherit from ApiTail', () => {
    const note = new ApiNote();
    note.string = 'N001';
    note.tail = 'test note';
    expect(note.string).toBe('N001');
    expect(note.tail).toBe('test note');
  });
});
