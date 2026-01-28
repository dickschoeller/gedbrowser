import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { TestBed } from '@angular/core/testing';
import { describe, it, expect, beforeEach, afterEach } from 'vitest';

import { SourceService } from './source.service';
import { ApiSource } from '../models';
import { UrlBuilder } from '../utils/urlbuilder';

describe('SourceService', () => {
  let service: SourceService;
  let httpMock: HttpTestingController;
  const testDb = 'testdb';

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [SourceService],
      imports: [HttpClientTestingModule]
    });
    service = TestBed.inject(SourceService);
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

  describe('url()', () => {
    it('should return sources endpoint URL', () => {
      const url = service.url(testDb);
      expect(url).toBe('/gedbrowserng/v1/dbs/testdb/sources');
    });
  });

  describe('post()', () => {
    it('should create new source', async () => {
      const newSource: ApiSource = {
        string: 'S1',
        dataset: testDb
      } as ApiSource;

      const result$ = service.post(testDb, newSource);
      const promise = result$.toPromise();

      const req = httpMock.expectOne('/gedbrowserng/v1/dbs/testdb/sources');
      expect(req.request.method).toBe('POST');
      req.flush(newSource);

      const result = await promise;
      expect(result).toEqual(newSource);
    });
  });

  describe('getAll()', () => {
    it('should fetch all sources', async () => {
      const sources: ApiSource[] = [
        { string: 'S1', dataset: testDb } as ApiSource,
        { string: 'S2', dataset: testDb } as ApiSource
      ];

      const result$ = service.getAll(testDb);
      const promise = result$.toPromise();

      const req = httpMock.expectOne('/gedbrowserng/v1/dbs/testdb/sources');
      req.flush(sources);

      const result = await promise;
      expect(result).toEqual(sources);
    });
  });

  describe('getOne()', () => {
    it('should fetch single source by ID', async () => {
      const source: ApiSource = {
        string: 'S1',
        dataset: testDb
      } as ApiSource;

      const result$ = service.getOne(testDb, 'S1');
      const promise = result$.toPromise();

      const req = httpMock.expectOne('/gedbrowserng/v1/dbs/testdb/sources/S1');
      req.flush(source);

      const result = await promise;
      expect(result).toEqual(source);
    });
  });

  describe('put()', () => {
    it('should update existing source', async () => {
      const source: ApiSource = {
        string: 'S1',
        dataset: testDb
      } as ApiSource;

      const result$ = service.put(testDb, source);
      const promise = result$.toPromise();

      const req = httpMock.expectOne('/gedbrowserng/v1/dbs/testdb/sources/S1');
      expect(req.request.method).toBe('PUT');
      req.flush(source);

      const result = await promise;
      expect(result).toEqual(source);
    });
  });

  describe('delete()', () => {
    it('should delete source', async () => {
      const source: ApiSource = {
        string: 'S1',
        dataset: testDb
      } as ApiSource;

      const result$ = service.delete(testDb, source);
      const promise = result$.toPromise();

      const req = httpMock.expectOne('/gedbrowserng/v1/dbs/testdb/sources/S1');
      expect(req.request.method).toBe('DELETE');
      req.flush(source);

      const result = await promise;
      expect(result).toEqual(source);
    });
  });

  describe('postLink()', () => {
    it('should create linked resource', async () => {
      const ub = new UrlBuilder(testDb, 'sources', 'references');
      const source: ApiSource = {
        string: 'S2',
        dataset: testDb
      } as ApiSource;

      const result$ = service.postLink(ub, 'S1', source);
      const promise = result$.toPromise();

      const req = httpMock.expectOne((r) => r.url.includes('/sources/S1/references'));
      expect(req.request.method).toBe('POST');
      req.flush(source);

      const result = await promise;
      expect(result).toEqual(source);
    });
  });

  describe('baseUrl()', () => {
    it('should return base URL for database', () => {
      const url = service.baseUrl(testDb);
      expect(url).toBe('/gedbrowserng/v1/dbs/testdb');
    });
  });
});
