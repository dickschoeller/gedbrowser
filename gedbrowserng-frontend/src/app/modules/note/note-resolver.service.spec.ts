import { HttpClientTestingModule } from '@angular/common/http/testing';
import { RouterTestingModule } from '@angular/router/testing';
import { TestBed } from '@angular/core/testing';

import { NoteResolverService } from './note-resolver.service';
import { NoteService } from '../../services';

describe('NoteResolverService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [ HttpClientTestingModule, RouterTestingModule ],
      providers: [NoteResolverService, NoteService]
    });
  });

  it('should be created', () => {
    const service: NoteResolverService = TestBed.inject(NoteResolverService);
    expect(service).toBeTruthy();
  });
});
