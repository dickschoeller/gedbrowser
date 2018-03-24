import { TestBed, inject } from '@angular/core/testing';

import { SpouseService } from './spouse.service';

describe('SpouseService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [SpouseService]
    });
  });

  it('should be created', inject([SpouseService], (service: SpouseService) => {
    expect(service).toBeTruthy();
  }));
});
