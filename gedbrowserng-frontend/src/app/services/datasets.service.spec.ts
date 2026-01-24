import { TestBed } from '@angular/core/testing';

import { DatasetsService } from './datasets.service';

describe('DatasetsService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [DatasetsService]
    });
  });

  it('should be created', () => {
    const service: DatasetsService = TestBed.inject(DatasetsService);
    expect(service).toBeTruthy();
  });
});
