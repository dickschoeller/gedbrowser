import { TestBed, inject } from '@angular/core/testing';
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

  it('should be created', inject([NoteListResolverService], (service: NoteListResolverService) => {
    expect(service).toBeTruthy();
  }));
});
