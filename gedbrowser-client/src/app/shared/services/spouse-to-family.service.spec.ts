import { TestBed, inject } from '@angular/core/testing';

import { SpouseToFamilyService } from './spouse-to-family.service';

describe('SpouseToFamilyService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [SpouseToFamilyService]
    });
  });

  it('should be created', inject([SpouseToFamilyService], (service: SpouseToFamilyService) => {
    expect(service).toBeTruthy();
  }));
});
