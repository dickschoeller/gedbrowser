import {TestBed, inject} from '@angular/core/testing';
import {HttpClientTestingModule} from '@angular/common/http/testing';
import {RouterTestingModule} from '@angular/router/testing';

import {PersonService} from '../../services';
import {PersonListResolverService} from './person-list-resolver.service';

describe('PersonListResolverService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [
        PersonListResolverService,
        PersonService
      ],
      imports: [
        HttpClientTestingModule,
        RouterTestingModule,
      ]
    });
  });

  it('should be created', inject([PersonListResolverService], (service: PersonListResolverService) => {
    expect(service).toBeTruthy();
  }));
});
