import {HttpClientTestingModule} from '@angular/common/http/testing';
import {TestBed} from '@angular/core/testing';

import {SubmitterService} from './submitter.service';

describe('SubmitterService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [SubmitterService],
      imports: [HttpClientTestingModule]
    });
  });

  it('should be created', () => {
    const service: SubmitterService = TestBed.inject(SubmitterService);
    expect(service).toBeTruthy();
  });
});
