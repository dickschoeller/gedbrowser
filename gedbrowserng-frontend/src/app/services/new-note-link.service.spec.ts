import { TestBed, inject } from '@angular/core/testing';

import { NewNoteLinkService } from './new-note-link.service';

describe('NewNoteLinkService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [NewNoteLinkService]
    });
  });

  it('should be created', inject([NewNoteLinkService], (service: NewNoteLinkService) => {
    expect(service).toBeTruthy();
  }));
});
