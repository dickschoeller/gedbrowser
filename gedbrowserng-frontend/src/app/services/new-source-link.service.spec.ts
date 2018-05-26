import { TestBed, inject } from '@angular/core/testing';

import { NewSourceLinkService } from './new-source-link.service';

describe('NewSourceLinkService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [NewSourceLinkService]
    });
  });

  it('should be created', inject([NewSourceLinkService], (service: NewSourceLinkService) => {
    expect(service).toBeTruthy();
  }));
});
