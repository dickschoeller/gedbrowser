import {TestBed} from '@angular/core/testing';
import {HttpClientTestingModule} from '@angular/common/http/testing';
import {RouterTestingModule} from '@angular/router/testing';

import {SubmitterService} from '../../services';
import {SubmitterListResolverService} from './submitter-list-resolver.service';

describe('SubmitterListResolverService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [
        SubmitterListResolverService,
        SubmitterService,
      ],
      imports: [
        HttpClientTestingModule,
        RouterTestingModule,
      ]
    });
  });

  it('should be created', () => {
    const service: SubmitterListResolverService = TestBed.inject(SubmitterListResolverService);
    expect(service).toBeTruthy();
  });
});
