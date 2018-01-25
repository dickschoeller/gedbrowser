import { TestBed, inject } from '@angular/core/testing';

import { SubmitterResolverService } from './submitter-resolver.service';

describe('SubmitterResolverService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [SubmitterResolverService]
    });
  });

  it('should be created', inject([SubmitterResolverService], (service: SubmitterResolverService) => {
    expect(service).toBeTruthy();
  }));
});
