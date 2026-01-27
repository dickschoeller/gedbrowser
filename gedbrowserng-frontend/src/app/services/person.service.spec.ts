import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { TestBed } from '@angular/core/testing';
import { describe, it, expect, beforeEach, afterEach } from 'vitest';

import { PersonService } from './person.service';
import { ApiPerson } from '../models';
import { UrlBuilder } from '../utils/urlbuilder';

describe('PersonService', () => {
  let service: PersonService;
  let httpMock: HttpTestingController;
  const testDb = 'testdb';

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [PersonService]
    });

    service = TestBed.inject(PersonService);
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
    it('should return persons endpoint URL', () => {
      const url = service.url(testDb);
      expect(url).toBe('/gedbrowserng/v1/dbs/testdb/persons');
    });
  });

  describe('post()', () => {
    it('should create new person', async () => {
      const newPerson: ApiPerson = {
        string: 'I1',
        dataset: testDb
      } as ApiPerson;

      const result$ = service.post(testDb, newPerson);
      const promise = result$.toPromise();

      const req = httpMock.expectOne('/gedbrowserng/v1/dbs/testdb/persons');
      expect(req.request.method).toBe('POST');
      expect(req.request.body).toEqual(newPerson);
      req.flush(newPerson);

      const result = await promise;
      expect(result).toEqual(newPerson);
    });
  });

  describe('getAll()', () => {
    it('should fetch all persons', async () => {
      const persons: ApiPerson[] = [
        { string: 'I1', dataset: testDb } as ApiPerson,
        { string: 'I2', dataset: testDb } as ApiPerson
      ];

      const result$ = service.getAll(testDb);
      const promise = result$.toPromise();

      const req = httpMock.expectOne('/gedbrowserng/v1/dbs/testdb/persons');
      expect(req.request.method).toBe('GET');
      req.flush(persons);

      const result = await promise;
      expect(result).toEqual(persons);
      expect(result.length).toBe(2);
    });

    it('should handle empty list', async () => {
      const result$ = service.getAll(testDb);
      const promise = result$.toPromise();

      const req = httpMock.expectOne('/gedbrowserng/v1/dbs/testdb/persons');
      req.flush([]);

      const result = await promise;
      expect(result).toEqual([]);
    });
  });

  describe('getOne()', () => {
    it('should fetch single person by ID', async () => {
      const person: ApiPerson = {
        string: 'I1',
        dataset: testDb
      } as ApiPerson;

      const result$ = service.getOne(testDb, 'I1');
      const promise = result$.toPromise();

      const req = httpMock.expectOne('/gedbrowserng/v1/dbs/testdb/persons/I1');
      expect(req.request.method).toBe('GET');
      req.flush(person);

      const result = await promise;
      expect(result).toEqual(person);
    });

    it('should handle 404 errors', async () => {
      const promise = service.getOne(testDb, 'NOTFOUND').toPromise();

      const req = httpMock.expectOne('/gedbrowserng/v1/dbs/testdb/persons/NOTFOUND');
      req.flush('Not found', { status: 404, statusText: 'Not Found' });

      await expect(promise).rejects.toMatchObject({ status: 404 });
    });
  });

  describe('put()', () => {
    it('should update existing person', async () => {
      const person: ApiPerson = {
        string: 'I1',
        dataset: testDb
      } as ApiPerson;

      const result$ = service.put(testDb, person);
      const promise = result$.toPromise();

      const req = httpMock.expectOne('/gedbrowserng/v1/dbs/testdb/persons/I1');
      expect(req.request.method).toBe('PUT');
      expect(req.request.body).toEqual(person);
      req.flush(person);

      const result = await promise;
      expect(result).toEqual(person);
    });
  });

  describe('delete()', () => {
    it('should delete person', async () => {
      const person: ApiPerson = {
        string: 'I1',
        dataset: testDb
      } as ApiPerson;

      const result$ = service.delete(testDb, person);
      const promise = result$.toPromise();

      const req = httpMock.expectOne('/gedbrowserng/v1/dbs/testdb/persons/I1');
      expect(req.request.method).toBe('DELETE');
      req.flush(person);

      const result = await promise;
      expect(result).toEqual(person);
    });
  });

  describe('postLink()', () => {
    it('should create linked resource', async () => {
      const ub = new UrlBuilder(testDb, 'persons', 'children');
      const person: ApiPerson = {
        string: 'I2',
        dataset: testDb
      } as ApiPerson;

      const result$ = service.postLink(ub, 'I1', person);
      const promise = result$.toPromise();

      const req = httpMock.expectOne((r) => r.url.includes('/persons/I1/children'));
      expect(req.request.method).toBe('POST');
      expect(req.request.body).toEqual(person);
      req.flush(person);

      const result = await promise;
      expect(result).toEqual(person);
    });
  });

  describe('putLink()', () => {
    it('should update linked resource', async () => {
      const ub = new UrlBuilder(testDb, 'persons', 'children');
      const person: ApiPerson = {
        string: 'I2',
        dataset: testDb
      } as ApiPerson;

      const result$ = service.putLink(ub, 'I1', person);
      const promise = result$.toPromise();

      const req = httpMock.expectOne((r) => r.url.includes('/persons/I1/children'));
      expect(req.request.method).toBe('PUT');
      expect(req.request.body).toEqual(person);
      req.flush(person);

      const result = await promise;
      expect(result).toEqual(person);
    });
  });

  describe('deleteLink()', () => {
    it('should delete linked resource', async () => {
      const ub = new UrlBuilder(testDb, 'persons', 'children');
      const person: ApiPerson = {
        string: 'I2',
        dataset: testDb
      } as ApiPerson;

      const result$ = service.deleteLink(ub, 'I1', person);
      const promise = result$.toPromise();

      const req = httpMock.expectOne((r) => r.url.includes('/persons/I1/children/I2'));
      expect(req.request.method).toBe('DELETE');
      req.flush(person);

      const result = await promise;
      expect(result).toEqual(person);
    });
  });

  describe('baseUrl()', () => {
    it('should return base URL for database', () => {
      const url = service.baseUrl(testDb);
      expect(url).toBe('/gedbrowserng/v1/dbs/testdb');
    });
  });
});
