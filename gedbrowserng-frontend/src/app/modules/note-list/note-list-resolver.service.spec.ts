import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { RouterTestingModule } from '@angular/router/testing';

import { NoteService } from '../../services';
import { NoteListResolverService } from './note-list-resolver.service';

describe('NoteListResolverService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [
        NoteListResolverService,
        NoteService,
      ],
      imports: [
        HttpClientTestingModule,
        RouterTestingModule,
      ]
    });
  });

  it('should be created', () => {
    const service: NoteListResolverService = TestBed.inject(NoteListResolverService);
    expect(service).toBeTruthy();
  });
});
