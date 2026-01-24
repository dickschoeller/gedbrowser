import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { RouterTestingModule } from '@angular/router/testing';

import { PersonListResolverService } from './person-list-resolver.service';
import { PersonService } from '../../services';

describe('PersonListResolverService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [ HttpClientTestingModule, RouterTestingModule ],
      providers: [ PersonListResolverService, PersonService ]
    });
  });

  it('should be created', () => {
    const service: PersonListResolverService = TestBed.inject(PersonListResolverService);
    expect(service).toBeTruthy();
  });
});
