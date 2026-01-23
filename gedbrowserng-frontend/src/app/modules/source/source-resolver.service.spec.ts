import {TestBed} from '@angular/core/testing';

import {SourceResolverService} from './source-resolver.service';

describe('SourceResolverService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [SourceResolverService]
    });
  });

  it('should be created', () => {
    const service: SourceResolverService = TestBed.inject(SourceResolverService);
    expect(service).toBeTruthy();
  });
});
