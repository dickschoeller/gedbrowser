import { TestBed } from '@angular/core/testing';
import { HttpTestingController, provideHttpClientTesting } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';
import { throwError, of } from 'rxjs';
import { vi } from 'vitest';

import { MapKeyService } from './map-key.service';
import { AuthApiService } from './auth-api.service';
import { ConfigService } from './config.service';

describe('MapKeyService', () => {
  let service: MapKeyService;
  let httpMock: HttpTestingController;
  let apiService: AuthApiService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [
        provideHttpClient(),
        provideHttpClientTesting(),
        MapKeyService,
        { provide: ConfigService, useValue: { map_key_url: '/gedbrowserng/v1/map-key' } },
        { provide: AuthApiService, useValue: { get: vi.fn() } }
      ]
    });

    service = TestBed.inject(MapKeyService);
    httpMock = TestBed.inject(HttpTestingController);
    apiService = TestBed.inject(AuthApiService);
  });

  afterEach(() => {
    httpMock.verify();
  });

  it('returns key from direct HTTP GET when available', () => {
    service.getMapKey().subscribe((key) => {
      expect(key).toBe('MAP-KEY');
    });

    const req = httpMock.expectOne('/gedbrowserng/v1/map-key');
    expect(req.request.method).toBe('GET');
    req.flush('{"key":"MAP-KEY"}');
  });

  it('falls back to AuthApiService GET when direct GET fails', () => {
    vi.mocked(apiService.get as any).mockReturnValue(of({ key: 'FALLBACK-KEY' }));

    service.getMapKey().subscribe((key) => {
      expect(key).toBe('FALLBACK-KEY');
    });

    const req = httpMock.expectOne('/gedbrowserng/v1/map-key');
    req.flush('nope', { status: 500, statusText: 'Server Error' });
    expect(apiService.get).toHaveBeenCalledWith('/gedbrowserng/v1/map-key');
  });

  it('returns empty string when both primary and fallback calls fail', () => {
    vi.mocked(apiService.get as any).mockReturnValue(throwError(() => new Error('fallback failed')));

    service.getMapKey().subscribe((key) => {
      expect(key).toBe('');
    });

    const req = httpMock.expectOne('/gedbrowserng/v1/map-key');
    req.flush('nope', { status: 503, statusText: 'Unavailable' });
  });

  it('returns empty string when fallback payload has no key', () => {
    vi.mocked(apiService.get as any).mockReturnValue(of({}));

    service.getMapKey().subscribe((key) => {
      expect(key).toBe('');
    });

    const req = httpMock.expectOne('/gedbrowserng/v1/map-key');
    req.flush('nope', { status: 500, statusText: 'Server Error' });
  });

  it('returns empty string when response payload has no key', () => {
    service.getMapKey().subscribe((key) => {
      expect(key).toBe('');
    });

    const req = httpMock.expectOne('/gedbrowserng/v1/map-key');
    req.flush('{}');
  });

  it('returns trimmed key when direct GET responds with raw string', () => {
    service.getMapKey().subscribe((key) => {
      expect(key).toBe('KEY');
    });

    const req = httpMock.expectOne('/gedbrowserng/v1/map-key');
    req.flush('  KEY  ');
  });

  it('handles key field with surrounding whitespace', () => {
    service.getMapKey().subscribe((key) => {
      expect(key).toBe('RAW-KEY');
    });

    const req = httpMock.expectOne('/gedbrowserng/v1/map-key');
    req.flush('{"key":"  RAW-KEY  "}');
  });

  it('trims key field from object payload', () => {
    service.getMapKey().subscribe((key) => {
      expect(key).toBe('TRIM-ME');
    });

    const req = httpMock.expectOne('/gedbrowserng/v1/map-key');
    req.flush('{"key":"  TRIM-ME  "}');
  });
});