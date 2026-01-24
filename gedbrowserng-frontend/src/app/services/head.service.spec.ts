import {HttpClientTestingModule} from '@angular/common/http/testing';
import { TestBed } from '@angular/core/testing';

import { HeadService } from './head.service';

describe('HeadService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [HeadService],
      imports: [HttpClientTestingModule]
    });
  });

  it('should be created', () => {
    const service: HeadService = TestBed.inject(HeadService);
    expect(service).toBeTruthy();
  });
});
