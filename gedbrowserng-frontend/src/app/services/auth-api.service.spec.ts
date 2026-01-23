import { TestBed } from '@angular/core/testing';

import { AuthApiService } from './auth-api.service';

describe('AuthApiService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: AuthApiService = TestBed.inject(AuthApiService);
    expect(service).toBeTruthy();
  });
});
