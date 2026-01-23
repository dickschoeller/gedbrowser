import {TestBed} from '@angular/core/testing';

import {SubmitterResolverService} from './submitter-resolver.service';

describe('SubmitterResolverService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [SubmitterResolverService]
    });
  });

  it('should be created', () => {
    const service: SubmitterResolverService = TestBed.inject(SubmitterResolverService);
    expect(service).toBeTruthy();
  });
});
