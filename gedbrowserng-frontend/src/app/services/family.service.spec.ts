import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { TestBed } from '@angular/core/testing';
import { describe, it, expect, beforeEach, afterEach } from 'vitest';

import { FamilyService } from './family.service';
import { ApiFamily } from '../models';
import { UrlBuilder } from '../utils/urlbuilder';

describe('FamilyService', () => {
  let service: FamilyService;
  let httpMock: HttpTestingController;
  const testDb = 'testdb';

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [FamilyService],
      imports: [HttpClientTestingModule]
    });
    service = TestBed.inject(FamilyService);
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
    it('should return families endpoint URL', () => {
      const url = service.url(testDb);
      expect(url).toBe('/gedbrowserng/v1/dbs/testdb/families');
    });
  });

  describe('post()', () => {
    it('should create new family', async () => {
      const newFamily: ApiFamily = {
        string: 'F1',
        dataset: testDb
      } as ApiFamily;

      const result$ = service.post(testDb, newFamily);
      const promise = result$.toPromise();

      const req = httpMock.expectOne('/gedbrowserng/v1/dbs/testdb/families');
      expect(req.request.method).toBe('POST');
      req.flush(newFamily);

      const result = await promise;
      expect(result).toEqual(newFamily);
    });
  });

  describe('getAll()', () => {
    it('should fetch all families', async () => {
      const families: ApiFamily[] = [
        { string: 'F1', dataset: testDb } as ApiFamily,
        { string: 'F2', dataset: testDb } as ApiFamily
      ];

      const result$ = service.getAll(testDb);
      const promise = result$.toPromise();

      const req = httpMock.expectOne('/gedbrowserng/v1/dbs/testdb/families');
      req.flush(families);

      const result = await promise;
      expect(result).toEqual(families);
    });
  });

  describe('getOne()', () => {
    it('should fetch single family by ID', async () => {
      const family: ApiFamily = {
        string: 'F1',
        dataset: testDb
      } as ApiFamily;

      const result$ = service.getOne(testDb, 'F1');
      const promise = result$.toPromise();

      const req = httpMock.expectOne('/gedbrowserng/v1/dbs/testdb/families/F1');
      req.flush(family);

      const result = await promise;
      expect(result).toEqual(family);
    });
  });

  describe('put()', () => {
    it('should update existing family', async () => {
      const family: ApiFamily = {
        string: 'F1',
        dataset: testDb
      } as ApiFamily;

      const result$ = service.put(testDb, family);
      const promise = result$.toPromise();

      const req = httpMock.expectOne('/gedbrowserng/v1/dbs/testdb/families/F1');
      req.flush(family);

      const result = await promise;
      expect(result).toEqual(family);
    });
  });

  describe('delete()', () => {
    it('should delete family', async () => {
      const family: ApiFamily = {
        string: 'F1',
        dataset: testDb
      } as ApiFamily;

      const result$ = service.delete(testDb, family);
      const promise = result$.toPromise();

      const req = httpMock.expectOne('/gedbrowserng/v1/dbs/testdb/families/F1');
      req.flush(family);

      const result = await promise;
      expect(result).toEqual(family);
    });
  });

  describe('postLink()', () => {
    it('should create linked resource', async () => {
      const ub = new UrlBuilder(testDb, 'families', 'spouses');
      const family: ApiFamily = {
        string: 'F2',
        dataset: testDb
      } as ApiFamily;

      const result$ = service.postLink(ub, 'F1', family);
      const promise = result$.toPromise();

      const req = httpMock.expectOne((r) => r.url.includes('/families/F1/spouses'));
      req.flush(family);

      const result = await promise;
      expect(result).toEqual(family);
    });
  });

  describe('putLink()', () => {
    it('should update linked resource', async () => {
      const ub = new UrlBuilder(testDb, 'families', 'spouses');
      const family: ApiFamily = {
        string: 'F2',
        dataset: testDb
      } as ApiFamily;

      const result$ = service.putLink(ub, 'F1', family);
      const promise = result$.toPromise();

      const req = httpMock.expectOne((r) => r.url.includes('/families/F1/spouses'));
      req.flush(family);

      const result = await promise;
      expect(result).toEqual(family);
    });
  });

  describe('deleteLink()', () => {
    it('should delete linked resource', async () => {
      const ub = new UrlBuilder(testDb, 'families', 'spouses');
      const family: ApiFamily = {
        string: 'F2',
        dataset: testDb
      } as ApiFamily;

      const result$ = service.deleteLink(ub, 'F1', family);
      const promise = result$.toPromise();

      const req = httpMock.expectOne((r) => r.url.includes('/families/F1/spouses/F2'));
      req.flush(family);

      const result = await promise;
      expect(result).toEqual(family);
    });
  });

  describe('baseUrl()', () => {
    it('should return base URL for database', () => {
      const url = service.baseUrl(testDb);
      expect(url).toBe('/gedbrowserng/v1/dbs/testdb');
    });
  });
});
