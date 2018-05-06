import {HttpClientTestingModule} from '@angular/common/http/testing';
import {TestBed, inject} from '@angular/core/testing';

import {SubmitterService} from './submitter.service';

describe('SubmitterService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [SubmitterService],
      imports: [HttpClientTestingModule]
    });
  });

  it('should be created', inject([SubmitterService], (service: SubmitterService) => {
    expect(service).toBeTruthy();
  }));
});
