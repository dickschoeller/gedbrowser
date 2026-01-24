import {HttpClientTestingModule} from '@angular/common/http/testing';
import {TestBed} from '@angular/core/testing';

import {SourceService} from './source.service';

describe('SourceService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [SourceService],
      imports: [HttpClientTestingModule]
    });
  });

  it('should be created', () => {
    const service: SourceService = TestBed.inject(SourceService);
    expect(service).toBeTruthy();
  });
});
