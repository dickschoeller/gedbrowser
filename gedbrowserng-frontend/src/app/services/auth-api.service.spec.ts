import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpHeaders } from '@angular/common/http';
import { AuthApiService, RequestMethod } from './auth-api.service';

describe('AuthApiService', () => {
  let service: AuthApiService;
  let httpMock: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [AuthApiService],
    });

    service = TestBed.inject(AuthApiService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    httpMock.verify();
  });

  it('performs GET with default headers and withCredentials', () => {
    service.get('/path').subscribe((resp) => {
      expect(resp).toEqual({ ok: true });
    });

    const req = httpMock.expectOne((r) => r.method === 'GET' && r.url === '/path');
    expect(req.request.withCredentials).toBe(true);
    expect(req.request.headers.get('Accept')).toBe('application/json');
    expect(req.request.headers.get('Content-Type')).toBe('application/json');
    expect(req.request.params.keys().length).toBe(0);
    req.flush({ ok: true });
  });

  it('serializes params on GET', () => {
    service.get('/path', { a: 1, b: 'two' }).subscribe();

    const req = httpMock.expectOne((r) => r.method === 'GET' && r.url === '/path');
    expect(req.request.params.get('a')).toBe('1');
    expect(req.request.params.get('b')).toBe('two');
    req.flush({});
  });

  it('POST uses request() with body and default headers', () => {
    service.post('/path', { val: 5 }).subscribe((resp) => {
      expect(resp).toEqual({ ok: true });
    });

    const req = httpMock.expectOne('/path');
    expect(req.request.method).toBe(RequestMethod.Post);
    expect(req.request.body).toEqual({ val: 5 });
    expect(req.request.withCredentials).toBe(true);
    req.flush({ ok: true });
  });

  it('PUT uses request() with body', () => {
    service.put('/path', { val: 10 }).subscribe();

    const req = httpMock.expectOne('/path');
    expect(req.request.method).toBe(RequestMethod.Put);
    expect(req.request.body).toEqual({ val: 10 });
    req.flush({});
  });

  it('DELETE supports body payload', () => {
    service.delete('/path', { id: 7 }).subscribe();

    const req = httpMock.expectOne('/path');
    expect(req.request.method).toBe(RequestMethod.Delete);
    expect(req.request.body).toEqual({ id: 7 });
    req.flush({});
  });

  it('request uses custom headers when provided', () => {
    const custom = new HttpHeaders({ 'X-Test': 'yes' });
    service.post('/path', { v: 1 }, custom).subscribe();

    const req = httpMock.expectOne('/path');
    expect(req.request.headers.get('X-Test')).toBe('yes');
    expect(req.request.headers.get('Content-Type')).toBeNull();
    req.flush({});
  });

  it('checkError rethrows 401 and non-401', () => {
    expect(() => (service as any).checkError({ status: 401 })).toThrow();
    expect(() => (service as any).checkError({ status: 500 })).toThrow();
  });
});
