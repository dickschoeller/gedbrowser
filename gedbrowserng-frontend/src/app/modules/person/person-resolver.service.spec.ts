import {TestBed} from '@angular/core/testing';

import {PersonResolverService} from './person-resolver.service';

describe('PersonResolverService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [PersonResolverService]
    });
  });

  it('should be created', () => {
    const service: PersonResolverService = TestBed.inject(PersonResolverService);
    expect(service).toBeTruthy();
  });
});
