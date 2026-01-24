import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { RouterTestingModule } from '@angular/router/testing';

import { SourceListResolverService } from './source-list-resolver.service';
import { SourceService } from '../../services';

describe('SourceListResolverService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [ HttpClientTestingModule, RouterTestingModule ],
      providers: [ SourceListResolverService, SourceService ]
    });
  });

  it('should be created', () => {
    const service: SourceListResolverService = TestBed.inject(SourceListResolverService);
    expect(service).toBeTruthy();
  });
});
