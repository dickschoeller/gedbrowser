import { TestBed, inject } from '@angular/core/testing';

import { ChildToFamilyService } from './child-to-family.service';

describe('ChildToFamilyService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [ChildToFamilyService]
    });
  });

  it('should be created', inject([ChildToFamilyService], (service: ChildToFamilyService) => {
    expect(service).toBeTruthy();
  }));
});
