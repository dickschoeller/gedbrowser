import {TestBed, inject} from '@angular/core/testing';
import {HttpClientTestingModule} from '@angular/common/http/testing';
import {RouterTestingModule} from '@angular/router/testing';

import {SourceService} from '../../services';
import {SourceListResolverService} from './source-list-resolver.service';

describe('SourceListResolverService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [
        SourceListResolverService,
        SourceService,
      ],
      imports: [
        HttpClientTestingModule,
        RouterTestingModule,
      ]
    });
  });

  it('should be created', inject([SourceListResolverService], (service: SourceListResolverService) => {
    expect(service).toBeTruthy();
  }));
});
