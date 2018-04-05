import { TestBed, inject } from '@angular/core/testing';

import { PersonResolver } from './person-resolver.service';

describe('PersonResolver', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [PersonResolver]
    });
  });

  it('should be created', inject([PersonResolver], (service: PersonResolver) => {
    expect(service).toBeTruthy();
  }));
});
