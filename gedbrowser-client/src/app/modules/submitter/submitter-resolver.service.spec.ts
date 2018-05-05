import { TestBed, inject } from '@angular/core/testing';

import { SubmitterResolver } from './submitter-resolver.service';

describe('SubmitterResolver', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [SubmitterResolver]
    });
  });

  it('should be created', inject([SubmitterResolver], (service: SubmitterResolver) => {
    expect(service).toBeTruthy();
  }));
});
