import { HttpClientTestingModule } from '@angular/common/http/testing';
import { RouterTestingModule } from '@angular/router/testing';
import {TestBed} from '@angular/core/testing';

import {SubmitterResolverService} from './submitter-resolver.service';
import { SubmitterService } from '../../services';

describe('SubmitterResolverService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [ HttpClientTestingModule, RouterTestingModule ],
      providers: [SubmitterResolverService, SubmitterService]
    });
  });

  it('should be created', () => {
    const service: SubmitterResolverService = TestBed.inject(SubmitterResolverService);
    expect(service).toBeTruthy();
  });
});
