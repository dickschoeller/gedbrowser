import { TestBed, inject } from '@angular/core/testing';

import { ChildToPersonService } from './child-to-person.service';

describe('ChildToPersonService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [ChildToPersonService]
    });
  });

  it('should be created', inject([ChildToPersonService], (service: ChildToPersonService) => {
    expect(service).toBeTruthy();
  }));
});
