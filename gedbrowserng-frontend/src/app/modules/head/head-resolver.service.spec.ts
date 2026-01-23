import {TestBed} from '@angular/core/testing';

import {HeadResolverService} from './head-resolver.service';

describe('HeadResolverService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [HeadResolverService]
    });
  });

  it('should be created', () => {
    const service: HeadResolverService = TestBed.inject(HeadResolverService);
    expect(service).toBeTruthy();
  });
});
