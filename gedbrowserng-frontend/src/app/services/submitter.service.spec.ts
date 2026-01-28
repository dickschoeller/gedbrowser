import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { TestBed } from '@angular/core/testing';
import { describe, it, expect, beforeEach, afterEach } from 'vitest';

import { SubmitterService } from './submitter.service';
import { ApiSubmitter } from '../models';
import { UrlBuilder } from '../utils/urlbuilder';

describe('SubmitterService', () => {
  let service: SubmitterService;
  let httpMock: HttpTestingController;
  const testDb = 'testdb';

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [SubmitterService],
      imports: [HttpClientTestingModule]
    });
    service = TestBed.inject(SubmitterService);
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
    it('should return submitters endpoint URL', () => {
      const url = service.url(testDb);
      expect(url).toBe('/gedbrowserng/v1/dbs/testdb/submitters');
    });
  });

  describe('post()', () => {
    it('should create new submitter', async () => {
      const newSubmitter: ApiSubmitter = {
        string: 'SUBM1',
        dataset: testDb
      } as ApiSubmitter;

      const result$ = service.post(testDb, newSubmitter);
      const promise = result$.toPromise();

      const req = httpMock.expectOne('/gedbrowserng/v1/dbs/testdb/submitters');
      expect(req.request.method).toBe('POST');
      req.flush(newSubmitter);

      const result = await promise;
      expect(result).toEqual(newSubmitter);
    });
  });

  describe('getAll()', () => {
    it('should fetch all submitters', async () => {
      const submitters: ApiSubmitter[] = [
        { string: 'SUBM1', dataset: testDb } as ApiSubmitter,
        { string: 'SUBM2', dataset: testDb } as ApiSubmitter
      ];

      const result$ = service.getAll(testDb);
      const promise = result$.toPromise();

      const req = httpMock.expectOne('/gedbrowserng/v1/dbs/testdb/submitters');
      req.flush(submitters);

      const result = await promise;
      expect(result).toEqual(submitters);
    });
  });

  describe('getOne()', () => {
    it('should fetch single submitter by ID', async () => {
      const submitter: ApiSubmitter = {
        string: 'SUBM1',
        dataset: testDb
      } as ApiSubmitter;

      const result$ = service.getOne(testDb, 'SUBM1');
      const promise = result$.toPromise();

      const req = httpMock.expectOne('/gedbrowserng/v1/dbs/testdb/submitters/SUBM1');
      req.flush(submitter);

      const result = await promise;
      expect(result).toEqual(submitter);
    });
  });

  describe('put()', () => {
    it('should update existing submitter', async () => {
      const submitter: ApiSubmitter = {
        string: 'SUBM1',
        dataset: testDb
      } as ApiSubmitter;

      const result$ = service.put(testDb, submitter);
      const promise = result$.toPromise();

      const req = httpMock.expectOne('/gedbrowserng/v1/dbs/testdb/submitters/SUBM1');
      expect(req.request.method).toBe('PUT');
      req.flush(submitter);

      const result = await promise;
      expect(result).toEqual(submitter);
    });
  });

  describe('delete()', () => {
    it('should delete submitter', async () => {
      const submitter: ApiSubmitter = {
        string: 'SUBM1',
        dataset: testDb
      } as ApiSubmitter;

      const result$ = service.delete(testDb, submitter);
      const promise = result$.toPromise();

      const req = httpMock.expectOne('/gedbrowserng/v1/dbs/testdb/submitters/SUBM1');
      expect(req.request.method).toBe('DELETE');
      req.flush(submitter);

      const result = await promise;
      expect(result).toEqual(submitter);
    });
  });

  describe('postLink()', () => {
    it('should create linked resource', async () => {
      const ub = new UrlBuilder(testDb, 'submitters', 'submissions');
      const submitter: ApiSubmitter = {
        string: 'SUBM2',
        dataset: testDb
      } as ApiSubmitter;

      const result$ = service.postLink(ub, 'SUBM1', submitter);
      const promise = result$.toPromise();

      const req = httpMock.expectOne((r) => r.url.includes('/submitters/SUBM1/submissions'));
      expect(req.request.method).toBe('POST');
      req.flush(submitter);

      const result = await promise;
      expect(result).toEqual(submitter);
    });
  });

  describe('baseUrl()', () => {
    it('should return base URL for database', () => {
      const url = service.baseUrl(testDb);
      expect(url).toBe('/gedbrowserng/v1/dbs/testdb');
    });
  });
});
