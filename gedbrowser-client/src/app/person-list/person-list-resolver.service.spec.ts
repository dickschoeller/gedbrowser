import { TestBed, inject } from '@angular/core/testing';

import { PersonsResolverService } from './persons-resolver.service';

describe('PersonsResolverService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [PersonsResolverService]
    });
  });

  it('should be created', inject([PersonsResolverService], (service: PersonsResolverService) => {
    expect(service).toBeTruthy();
  }));
});
