import { TestBed, inject } from '@angular/core/testing';

import { SubmitterListResolver } from './submitter-list-resolver.service';

describe('SubmitterListResolver', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [SubmitterListResolver]
    });
  });

  it('should be created', inject([SubmitterListResolver], (service: SubmitterListResolver) => {
    expect(service).toBeTruthy();
  }));
});
