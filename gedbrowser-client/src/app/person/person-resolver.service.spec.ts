import { TestBed, inject } from '@angular/core/testing';

import { PersonResolverService } from './person-resolver.service';

describe('PersonResolverService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [PersonResolverService]
    });
  });

  it('should be created', inject([PersonResolverService], (service: PersonResolverService) => {
    expect(service).toBeTruthy();
  }));
});
