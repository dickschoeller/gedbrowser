import {TestBed, inject} from '@angular/core/testing';

import {HeadResolverService} from './head-resolver.service';

describe('HeadResolverService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [HeadResolverService]
    });
  });

  it('should be created', inject([HeadResolverService], (service: HeadResolverService) => {
    expect(service).toBeTruthy();
  }));
});
