import {HttpClientTestingModule} from '@angular/common/http/testing';
import {TestBed} from '@angular/core/testing';

import {FamilyService} from './family.service';

describe('FamilyService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [FamilyService],
      imports: [HttpClientTestingModule]
    });
  });

  it('should be created', () => {
    const service: FamilyService = TestBed.inject(FamilyService);
    expect(service).toBeTruthy();
  });
});
