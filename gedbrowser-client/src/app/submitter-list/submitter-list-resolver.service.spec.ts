import { TestBed, inject } from '@angular/core/testing';

import { SubmittersResolverService } from './submitters-resolver.service';

describe('SubmittersResolverService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [SubmittersResolverService]
    });
  });

  it('should be created', inject([SubmittersResolverService], (service: SubmittersResolverService) => {
    expect(service).toBeTruthy();
  }));
});
