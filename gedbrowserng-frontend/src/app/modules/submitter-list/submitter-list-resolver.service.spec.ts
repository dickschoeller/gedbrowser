import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { RouterTestingModule } from '@angular/router/testing';

import { SubmitterListResolverService } from './submitter-list-resolver.service';
import { SubmitterService } from '../../services';

describe('SubmitterListResolverService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [ HttpClientTestingModule, RouterTestingModule ],
      providers: [ SubmitterListResolverService, SubmitterService ]
    });
  });

  it('should be created', () => {
    const service: SubmitterListResolverService = TestBed.inject(SubmitterListResolverService);
    expect(service).toBeTruthy();
  });
});
