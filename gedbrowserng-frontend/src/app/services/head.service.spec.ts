import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { TestBed } from '@angular/core/testing';
import { describe, it, expect, beforeEach, afterEach } from 'vitest';

import { HeadService } from './head.service';
import { ApiHead } from '../models';

describe('HeadService', () => {
  let service: HeadService;
  let httpMock: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [HeadService],
      imports: [HttpClientTestingModule]
    });

    service = TestBed.inject(HeadService);
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

  describe('getOne()', () => {
    it('should fetch head from database endpoint', () => {
      const db = 'testdb';
      const mockHead: ApiHead = {
        string: 'HEAD',
        dataset: db,
        submitter: { string: 'SUBMITTER' },
        submissionLink: { string: 'SUBMISSION' }
      } as ApiHead;

      service.getOne(db).subscribe();

      const req = httpMock.expectOne('/gedbrowserng/v1/dbs/testdb');
      expect(req.request.method).toBe('GET');
      req.flush(mockHead);
    });

    it('should return ApiHead object', async () => {
      const db = 'testdb';
      const mockHead: ApiHead = {
        string: 'HEAD',
        dataset: db,
        submitter: { string: 'SUBMITTER' },
        submissionLink: { string: 'SUBMISSION' }
      } as ApiHead;

      const result$ = service.getOne(db);
      const promise = result$.toPromise();

      const req = httpMock.expectOne('/gedbrowserng/v1/dbs/testdb');
      req.flush(mockHead);

      const result = await promise;
      expect(result).toEqual(mockHead);
    });

    it('should handle HTTP errors', async () => {
      const db = 'testdb';

      const promise = service.getOne(db).toPromise();

      const req = httpMock.expectOne('/gedbrowserng/v1/dbs/testdb');
      req.flush('Not found', { status: 404, statusText: 'Not Found' });

      try {
        await promise;
        fail('should error');
      } catch (error) {
        expect(error.status).toBe(404);
      }
    });
  });

  describe('put()', () => {
    it('should update head at database endpoint', () => {
      const db = 'testdb';
      const mockHead: ApiHead = {
        string: 'HEAD',
        dataset: db,
        submitter: { string: 'SUBMITTER' },
        submissionLink: { string: 'SUBMISSION' }
      } as ApiHead;

      service.put(db, mockHead).subscribe();

      const req = httpMock.expectOne('/gedbrowserng/v1/dbs/testdb');
      expect(req.request.method).toBe('PUT');
      expect(req.request.body).toEqual(mockHead);
      req.flush(mockHead);
    });

    it('should return updated ApiHead object', async () => {
      const db = 'testdb';
      const mockHead: ApiHead = {
        string: 'HEAD',
        dataset: db,
        submitter: { string: 'SUBMITTER' },
        submissionLink: { string: 'SUBMISSION' }
      } as ApiHead;

      const result$ = service.put(db, mockHead);
      const promise = result$.toPromise();

      const req = httpMock.expectOne('/gedbrowserng/v1/dbs/testdb');
      req.flush(mockHead);

      const result = await promise;
      expect(result).toEqual(mockHead);
    });

    it('should handle HTTP errors', async () => {
      const db = 'testdb';
      const mockHead: ApiHead = {
        string: 'HEAD',
        dataset: db
      } as ApiHead;

      const promise = service.put(db, mockHead).toPromise();

      const req = httpMock.expectOne('/gedbrowserng/v1/dbs/testdb');
      req.flush('Server error', { status: 500, statusText: 'Internal Server Error' });

      try {
        await promise;
        fail('should error');
      } catch (error) {
        expect(error.status).toBe(500);
      }
    });
  });

  describe('url()', () => {
    it('should return base URL for database', () => {
      const db = 'testdb';
      const url = service.url(db);
      
      expect(url).toBe('/gedbrowserng/v1/dbs/testdb');
    });

    it('should construct URL with UrlBuilder', () => {
      const db = 'mydb';
      const url = service.url(db);
      
      expect(url).toBe('/gedbrowserng/v1/dbs/mydb');
    });
  });
});
