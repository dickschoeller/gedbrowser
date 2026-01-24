import { HttpClientTestingModule } from '@angular/common/http/testing';
import { RouterTestingModule } from '@angular/router/testing';
import {TestBed} from '@angular/core/testing';

import {HeadResolverService} from './head-resolver.service';
import { HeadService } from '../../services';

describe('HeadResolverService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [ HttpClientTestingModule, RouterTestingModule ],
      providers: [HeadResolverService, HeadService]
    });
  });

  it('should be created', () => {
    const service: HeadResolverService = TestBed.inject(HeadResolverService);
    expect(service).toBeTruthy();
  });
});
