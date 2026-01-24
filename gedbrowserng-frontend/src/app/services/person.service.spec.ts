import {TestBed} from '@angular/core/testing';
import {HttpClientTestingModule} from '@angular/common/http/testing';

import {PersonService} from './person.service';

describe('PersonService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [PersonService],
      imports: [HttpClientTestingModule]
    });
  });

  it('should be created', () => {
    const service: PersonService = TestBed.inject(PersonService);
    expect(service).toBeTruthy();
  });
});
