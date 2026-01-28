import { TestBed } from '@angular/core/testing';

import { NoteListModule } from './note-list.module';

describe('NoteListModule', () => {
  it('should instantiate', () => {
    const module = TestBed.configureTestingModule({
      imports: [NoteListModule]
    }).inject(NoteListModule);
    expect(module).toBeTruthy();
  });
});
