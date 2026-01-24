import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { RouterTestingModule } from '@angular/router/testing';

import { NoteListResolverService } from './note-list-resolver.service';
import { NoteService } from '../../services';

describe('NoteListResolverService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [ HttpClientTestingModule, RouterTestingModule ],
      providers: [ NoteListResolverService, NoteService ]
    });
  });

  it('should be created', () => {
    const service: NoteListResolverService = TestBed.inject(NoteListResolverService);
    expect(service).toBeTruthy();
  });
});
