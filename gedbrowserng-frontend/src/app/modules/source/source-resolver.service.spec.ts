import { HttpClientTestingModule } from '@angular/common/http/testing';
import { RouterTestingModule } from '@angular/router/testing';
import {TestBed} from '@angular/core/testing';

import {SourceResolverService} from './source-resolver.service';
import { SourceService } from '../../services';

describe('SourceResolverService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [ HttpClientTestingModule, RouterTestingModule ],
      providers: [SourceResolverService, SourceService]
    });
  });

  it('should be created', () => {
    const service: SourceResolverService = TestBed.inject(SourceResolverService);
    expect(service).toBeTruthy();
  });
});
