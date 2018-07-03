import { TestBed, inject } from '@angular/core/testing';

import { NewSubmitterLinkService } from './new-submitter-link.service';

describe('NewSubmitterLinkService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [NewSubmitterLinkService]
    });
  });

  it('should be created', inject([NewSubmitterLinkService], (service: NewSubmitterLinkService) => {
    expect(service).toBeTruthy();
  }));
});
