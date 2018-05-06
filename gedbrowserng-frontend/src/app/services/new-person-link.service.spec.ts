import {HttpClientTestingModule} from '@angular/common/http/testing';
import {TestBed, inject} from '@angular/core/testing';

import {NewPersonLinkService} from './new-person-link.service';

describe('NewPersonLinkService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [NewPersonLinkService],
      imports: [HttpClientTestingModule]
    });
  });

  it('should be created', inject([NewPersonLinkService], (service: NewPersonLinkService) => {
    expect(service).toBeTruthy();
  }));
});
