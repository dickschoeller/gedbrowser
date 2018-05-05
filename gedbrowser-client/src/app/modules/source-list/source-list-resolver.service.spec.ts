import { TestBed, inject } from '@angular/core/testing';

import { SourceListResolver } from './source-list-resolver.service';

describe('SourceListResolver', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [SourceListResolver]
    });
  });

  it('should be created', inject([SourceListResolver], (service: SourceListResolver) => {
    expect(service).toBeTruthy();
  }));
});
