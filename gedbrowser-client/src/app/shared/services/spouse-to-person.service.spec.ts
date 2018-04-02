import { TestBed, inject } from '@angular/core/testing';

import { SpouseToPersonService } from './spouse-to-person.service';

describe('SpouseToPersonService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [SpouseToPersonService]
    });
  });

  it('should be created', inject([SpouseToPersonService], (service: SpouseToPersonService) => {
    expect(service).toBeTruthy();
  }));
});
