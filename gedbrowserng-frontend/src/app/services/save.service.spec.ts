import { HttpClient } from '@angular/common/http';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { TestBed } from '@angular/core/testing';
import { describe, it, expect, beforeEach, afterEach } from 'vitest';

import { SaveService } from './save.service';

describe('SaveService', () => {
  let service: SaveService;
  let httpMock: HttpTestingController;
  let httpClient: HttpClient;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule]
    });

    httpClient = TestBed.inject(HttpClient);
    httpMock = TestBed.inject(HttpTestingController);
    // Create service manually with injected HttpClient to avoid DI issues
    service = new SaveService(httpClient);
  });

  afterEach(() => {
    httpMock.verify();
  });

  describe('service creation', () => {
    it('should be created', () => {
      expect(service).toBeTruthy();
    });
  });

  describe('getTextFile()', () => {
    it('should fetch text file from save endpoint', async () => {
      const dataset = 'testdb';
      const expectedContent = 'GEDCOM file content';

      const result$ = service.getTextFile(dataset);
      const promise = result$.toPromise();

      const req = httpMock.expectOne('/gedbrowserng/v1/dbs/testdb/save');
      expect(req.request.method).toBe('GET');
      expect(req.request.responseType).toBe('text');
      req.flush(expectedContent);

      const result = await promise;
      expect(result).toBe(expectedContent);
    });

    it('should handle different dataset names', async () => {
      const dataset1 = 'database1';
      const content1 = 'content1';

      const result1$ = service.getTextFile(dataset1);
      const promise1 = result1$.toPromise();

      const req1 = httpMock.expectOne('/gedbrowserng/v1/dbs/database1/save');
      req1.flush(content1);

      const result1 = await promise1;
      expect(result1).toBe(content1);
    });

    it('should return empty string for empty response', async () => {
      const dataset = 'testdb';

      const result$ = service.getTextFile(dataset);
      const promise = result$.toPromise();

      const req = httpMock.expectOne('/gedbrowserng/v1/dbs/testdb/save');
      req.flush('');

      const result = await promise;
      expect(result).toBe('');
    });

    it('should handle large text content', async () => {
      const dataset = 'testdb';
      const largeContent = 'x'.repeat(100000);

      const result$ = service.getTextFile(dataset);
      const promise = result$.toPromise();

      const req = httpMock.expectOne('/gedbrowserng/v1/dbs/testdb/save');
      req.flush(largeContent);

      const result = await promise;
      expect(result).toBe(largeContent);
      expect(result.length).toBe(100000);
    });

    it('should handle HTTP 404 errors', async () => {
      const dataset = 'notfound';

      const promise = service.getTextFile(dataset).toPromise();

      const req = httpMock.expectOne('/gedbrowserng/v1/dbs/notfound/save');
      req.flush('Not found', { status: 404, statusText: 'Not Found' });

      try {
        await promise;
        fail('should have thrown an error');
      } catch (error) {
        expect(error.status).toBe(404);
      }
    });

    it('should handle HTTP 500 errors', async () => {
      const dataset = 'testdb';

      const promise = service.getTextFile(dataset).toPromise();

      const req = httpMock.expectOne('/gedbrowserng/v1/dbs/testdb/save');
      req.flush('Server error', { status: 500, statusText: 'Internal Server Error' });

      try {
        await promise;
        fail('should have thrown an error');
      } catch (error) {
        expect(error.status).toBe(500);
      }
    });

    it('should use UrlBuilder to construct URL', async () => {
      const dataset = 'mydata';

      const result$ = service.getTextFile(dataset);
      const promise = result$.toPromise();

      const req = httpMock.expectOne((r) => 
        r.url === '/gedbrowserng/v1/dbs/mydata/save' && 
        r.responseType === 'text'
      );
      req.flush('test');

      await promise;
    });

    it('should handle special characters in dataset name', async () => {
      const dataset = 'test-db_2024';
      const content = 'GEDCOM data';

      const result$ = service.getTextFile(dataset);
      const promise = result$.toPromise();

      const req = httpMock.expectOne('/gedbrowserng/v1/dbs/test-db_2024/save');
      req.flush(content);

      const result = await promise;
      expect(result).toBe(content);
    });

    it('should request with correct responseType text', async () => {
      const dataset = 'testdb';

      service.getTextFile(dataset).subscribe();

      const req = httpMock.expectOne('/gedbrowserng/v1/dbs/testdb/save');
      expect(req.request.responseType).toBe('text');
      req.flush('content');
    });
  });
});
