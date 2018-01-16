import { TestBed, inject } from '@angular/core/testing';

import { SourcesResolverService } from './sources-resolver.service';

describe('SourcesResolverService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [SourcesResolverService]
    });
  });

  it('should be created', inject([SourcesResolverService], (service: SourcesResolverService) => {
    expect(service).toBeTruthy();
  }));
});
