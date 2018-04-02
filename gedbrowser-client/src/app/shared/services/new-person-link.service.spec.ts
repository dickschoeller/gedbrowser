import { TestBed, inject } from '@angular/core/testing';

import { NewPersonLinkService } from './new-person-link.service';

describe('NewPersonLinkService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [NewPersonLinkService]
    });
  });

  it('should be created', inject([NewPersonLinkService], (service: NewPersonLinkService) => {
    expect(service).toBeTruthy();
  }));
});
