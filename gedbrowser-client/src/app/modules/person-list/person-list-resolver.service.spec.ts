import { TestBed, inject } from '@angular/core/testing';

import { PersonListResolver } from './person-list-resolver.service';

describe('PersonListResolverService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [PersonListResolver]
    });
  });

  it('should be created', inject([PersonListResolver], (service: PersonListResolver) => {
    expect(service).toBeTruthy();
  }));
});
