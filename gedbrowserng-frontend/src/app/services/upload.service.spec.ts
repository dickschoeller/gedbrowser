import { HttpClient, HttpEvent, HttpEventType, HttpResponse } from '@angular/common/http';
import { HttpTestingController, provideHttpClientTesting } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';
import { TestBed } from '@angular/core/testing';
import { describe, it, expect, beforeEach, afterEach } from 'vitest';

import { UploadService } from './upload.service';

describe('UploadService', () => {
  let service: UploadService;
  let httpMock: HttpTestingController;
  let httpClient: HttpClient;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [],
    providers: [
        provideHttpClient(),
        provideHttpClientTesting()
    ],
      providers: [UploadService]
    });

    httpClient = TestBed.inject(HttpClient);
    httpMock = TestBed.inject(HttpTestingController);
    // Create service manually with injected HttpClient to avoid DI issues
    service = new UploadService(httpClient);
  });

  afterEach(() => {
    httpMock.verify();
  });

  describe('service creation', () => {
    it('should be created', () => {
      expect(service).toBeTruthy();
    });
  });

  describe('uploadGedFile()', () => {
    it('should upload a file using FormData', async () => {
      const file = new File(['test content'], 'test.ged', { type: 'application/gedcom' });
      const responseData = { status: 'success' };

      const events: HttpEvent<Object>[] = [];
      service.uploadGedFile(file).subscribe((event) => {
        events.push(event);
      });

      const req = httpMock.expectOne('/gedbrowserng/v1/upload');
      expect(req.request.method).toBe('POST');
      expect(req.request.reportProgress).toBe(true);
      expect(req.request.body).toBeInstanceOf(FormData);

      const formData = req.request.body as FormData;
      expect(formData.has('file')).toBe(true);
      expect(formData.get('file')).toBe(file);

      req.flush(responseData);

      expect(events.length).toBeGreaterThan(0);
    });

    it('should make POST request with reportProgress enabled', () => {
      const file = new File(['content'], 'upload.ged');

      service.uploadGedFile(file).subscribe();

      const req = httpMock.expectOne('/gedbrowserng/v1/upload');
      expect(req.request.method).toBe('POST');
      expect(req.request.reportProgress).toBe(true);
      req.flush({});
    });

    it('should observe events for progress tracking', () => {
      const file = new File(['content'], 'test.ged');

      service.uploadGedFile(file).subscribe();

      const req = httpMock.expectOne('/gedbrowserng/v1/upload');
      // The request should be configured to observe events
      expect(req.request.reportProgress).toBe(true);
      req.flush({});
    });

    it('should send FormData with file appended', () => {
      const fileContent = 'GEDCOM data';
      const file = new File([fileContent], 'family.ged', { type: 'text/plain' });

      service.uploadGedFile(file).subscribe();

      const req = httpMock.expectOne('/gedbrowserng/v1/upload');
      const body = req.request.body as FormData;

      expect(body.has('file')).toBe(true);
      const uploadedFile = body.get('file') as File;
      expect(uploadedFile.name).toBe('family.ged');
      expect(uploadedFile.type).toBe('text/plain');

      req.flush({});
    });

    it('should use UrlBuilder to construct upload URL', () => {
      const file = new File(['test'], 'test.ged');

      service.uploadGedFile(file).subscribe();

      const req = httpMock.expectOne((r) => r.url === '/gedbrowserng/v1/upload');
      expect(req.request.url).toBe('/gedbrowserng/v1/upload');
      req.flush({});
    });

    it('should handle upload progress events', async () => {
      const file = new File(['content'], 'test.ged');
      const events: HttpEvent<Object>[] = [];

      service.uploadGedFile(file).subscribe((event) => {
        events.push(event);
      });

      const req = httpMock.expectOne('/gedbrowserng/v1/upload');

      // Simulate progress event
      req.event({
        type: HttpEventType.UploadProgress,
        loaded: 50,
        total: 100
      });

      // Complete the request
      req.flush({ status: 'success' });

      expect(events.length).toBeGreaterThan(0);
      const progressEvent = events.find(e => e.type === HttpEventType.UploadProgress);
      expect(progressEvent).toBeDefined();
    });

    it('should handle HTTP response event', () => {
      const file = new File(['content'], 'test.ged');
      const responseData = { id: '123', message: 'Upload successful' };
      let responseReceived = false;

      service.uploadGedFile(file).subscribe((event) => {
        if (event.type === HttpEventType.Response) {
          const httpResponse = event as HttpResponse<Object>;
          expect(httpResponse.body).toEqual(responseData);
          responseReceived = true;
        }
      });

      const req = httpMock.expectOne('/gedbrowserng/v1/upload');
      req.flush(responseData);

      expect(responseReceived).toBe(true);
    });

    it('should handle HTTP 500 errors', async () => {
      const file = new File(['content'], 'test.ged');
      let errorOccurred = false;

      service.uploadGedFile(file).subscribe({
        next: () => {},
        error: (error) => {
          expect(error.status).toBe(500);
          errorOccurred = true;
        }
      });

      const req = httpMock.expectOne('/gedbrowserng/v1/upload');
      req.flush('Server error', { status: 500, statusText: 'Internal Server Error' });

      expect(errorOccurred).toBe(true);
    });

    it('should handle HTTP 400 bad request errors', async () => {
      const file = new File(['invalid'], 'test.txt');
      let errorOccurred = false;

      service.uploadGedFile(file).subscribe({
        next: () => {},
        error: (error) => {
          expect(error.status).toBe(400);
          errorOccurred = true;
        }
      });

      const req = httpMock.expectOne('/gedbrowserng/v1/upload');
      req.flush('Bad request', { status: 400, statusText: 'Bad Request' });

      expect(errorOccurred).toBe(true);
    });

    it('should handle empty file upload', () => {
      const file = new File([], 'empty.ged');

      service.uploadGedFile(file).subscribe();

      const req = httpMock.expectOne('/gedbrowserng/v1/upload');
      const body = req.request.body as FormData;
      const uploadedFile = body.get('file') as File;
      expect(uploadedFile.size).toBe(0);

      req.flush({});
    });

    it('should handle large file upload', () => {
      const largeContent = 'x'.repeat(1000000);
      const file = new File([largeContent], 'large.ged');

      service.uploadGedFile(file).subscribe();

      const req = httpMock.expectOne('/gedbrowserng/v1/upload');
      const body = req.request.body as FormData;
      const uploadedFile = body.get('file') as File;
      expect(uploadedFile.size).toBe(1000000);

      req.flush({});
    });

    it('should handle different file types', () => {
      const file = new File(['data'], 'test.txt', { type: 'text/plain' });

      service.uploadGedFile(file).subscribe();

      const req = httpMock.expectOne('/gedbrowserng/v1/upload');
      const body = req.request.body as FormData;
      const uploadedFile = body.get('file') as File;
      expect(uploadedFile.type).toBe('text/plain');

      req.flush({});
    });

    it('should handle file with special characters in name', () => {
      const file = new File(['content'], 'my-family_tree (2024).ged');

      service.uploadGedFile(file).subscribe();

      const req = httpMock.expectOne('/gedbrowserng/v1/upload');
      const body = req.request.body as FormData;
      const uploadedFile = body.get('file') as File;
      expect(uploadedFile.name).toBe('my-family_tree (2024).ged');

      req.flush({});
    });

    it('should set responseType to json', () => {
      const file = new File(['content'], 'test.ged');

      service.uploadGedFile(file).subscribe();

      const req = httpMock.expectOne('/gedbrowserng/v1/upload');
      expect(req.request.responseType).toBe('json');
      req.flush({});
    });
  });
});
