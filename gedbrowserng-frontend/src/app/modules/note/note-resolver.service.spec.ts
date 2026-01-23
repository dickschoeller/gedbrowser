import { TestBed } from '@angular/core/testing';

import { NoteResolverService } from './note-resolver.service';

describe('NoteResolverService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [NoteResolverService]
    });
  });

  it('should be created', () => {
    const service: NoteResolverService = TestBed.inject(NoteResolverService);
    expect(service).toBeTruthy();
  });
});
