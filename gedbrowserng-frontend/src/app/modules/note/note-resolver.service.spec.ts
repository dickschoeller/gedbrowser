import { TestBed, inject } from '@angular/core/testing';

import { NoteResolverService } from './note-resolver.service';

describe('NoteResolverService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [NoteResolverService]
    });
  });

  it('should be created', inject([NoteResolverService], (service: NoteResolverService) => {
    expect(service).toBeTruthy();
  }));
});
