import { HttpTestingController, provideHttpClientTesting } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';
import { TestBed } from '@angular/core/testing';
import { describe, it, expect, beforeEach, afterEach } from 'vitest';
import { firstValueFrom } from 'rxjs';

import { DatasetsService } from './datasets.service';

describe('DatasetsService', () => {
  let service: DatasetsService;
  let httpMock: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [
        provideHttpClient(),
        provideHttpClientTesting(),
        DatasetsService
      ]
    });

    service = TestBed.inject(DatasetsService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    httpMock.verify();
  });

  describe('service creation', () => {
    it('should be created', () => {
      expect(service).toBeTruthy();
    });
  });

  describe('get()', () => {
    it('should fetch list of datasets', async () => {
      const datasets = ['db1', 'db2', 'db3'];

      const result$ = service.get();
      const promise = firstValueFrom(result$);

      const req = httpMock.expectOne('/gedbrowserng/v1/dbs');
      expect(req.request.method).toBe('GET');
      req.flush(datasets);

      const result = await promise;
      expect(result).toEqual(datasets);
      expect(result.length).toBe(3);
    });

    it('should handle empty dataset list', async () => {
      const result$ = service.get();
      const promise = firstValueFrom(result$);

      const req = httpMock.expectOne('/gedbrowserng/v1/dbs');
      req.flush([]);

      const result = await promise;
      expect(result).toEqual([]);
    });

    it('should deduplicate dataset names', async () => {
      const datasets = ['schoeller', 'schoeller', 'mini'];

      const result$ = service.get();
      const promise = firstValueFrom(result$);

      const req = httpMock.expectOne('/gedbrowserng/v1/dbs');
      req.flush(datasets);

      const result = await promise;
      expect(result).toEqual(['schoeller', 'mini']);
    });

    it('should handle HTTP errors', async () => {
      const promise = firstValueFrom(service.get());

      const req = httpMock.expectOne('/gedbrowserng/v1/dbs');
      req.flush('Server error', { status: 500, statusText: 'Internal Server Error' });

      await expect(promise).rejects.toMatchObject({ status: 500 });
    });
  });

  describe('url()', () => {
    it('should return datasets base URL', () => {
      const url = service.url();
      expect(url).toBe('/gedbrowserng/v1/dbs');
    });
  });
});
