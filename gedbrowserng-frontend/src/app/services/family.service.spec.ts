import {HttpClientTestingModule} from '@angular/common/http/testing';
import {TestBed, inject} from '@angular/core/testing';

import {FamilyService} from './family.service';

describe('FamilyService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [FamilyService],
      imports: [HttpClientTestingModule]
    });
  });

  it('should be created', inject([FamilyService], (service: FamilyService) => {
    expect(service).toBeTruthy();
  }));
});
