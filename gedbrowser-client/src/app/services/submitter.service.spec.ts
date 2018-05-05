import { TestBed, inject } from '@angular/core/testing';

import { SubmitterService } from './submitter.service';

describe('SubmitterService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [SubmitterService]
    });
  });

  it('should be created', inject([SubmitterService], (service: SubmitterService) => {
    expect(service).toBeTruthy();
  }));
});
