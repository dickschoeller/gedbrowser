import {TestBed, inject} from '@angular/core/testing';

import {SourceResolverService} from './source-resolver.service';

describe('SourceResolverService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [SourceResolverService]
    });
  });

  it('should be created', inject([SourceResolverService], (service: SourceResolverService) => {
    expect(service).toBeTruthy();
  }));
});
