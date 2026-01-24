import { HttpClientTestingModule } from '@angular/common/http/testing';
import { RouterTestingModule } from '@angular/router/testing';
import {TestBed} from '@angular/core/testing';

import {PersonResolverService} from './person-resolver.service';
import { PersonService } from '../../services';

describe('PersonResolverService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [ HttpClientTestingModule, RouterTestingModule ],
      providers: [PersonResolverService, PersonService]
    });
  });

  it('should be created', () => {
    const service: PersonResolverService = TestBed.inject(PersonResolverService);
    expect(service).toBeTruthy();
  });
});
