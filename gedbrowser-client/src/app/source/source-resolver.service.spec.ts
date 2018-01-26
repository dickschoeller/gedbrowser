import { TestBed, inject } from '@angular/core/testing';

import { SourceResolver } from './source-resolver.service';

describe('SourceResolver', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [SourceResolver]
    });
  });

  it('should be created', inject([SourceResolver], (service: SourceResolver) => {
    expect(service).toBeTruthy();
  }));
});
