import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { TestBed } from '@angular/core/testing';
import { Type } from '@angular/core';
import { afterEach, beforeEach, describe, expect, it } from 'vitest';
import { Observable, firstValueFrom } from 'rxjs';
import { UrlBuilder } from '../../utils/urlbuilder';

type LinkConfig = {
  parentId: string;
  childId: string;
  collection: string;
};

type LinkMethods = {
  post?: boolean;
  put?: boolean;
  delete?: boolean;
};

type CrudService<T> = {
  url: (db: string) => string;
  baseUrl: (db: string) => string;
  post: (db: string, entity: T) => Observable<T>;
  getAll: (db: string) => Observable<T[]>;
  getOne: (db: string, id: string) => Observable<T>;
  put: (db: string, entity: T) => Observable<T>;
  delete: (db: string, entity: T) => Observable<T>;
  postLink?: (ub: UrlBuilder, id: string, entity: T) => Observable<T>;
  putLink?: (ub: UrlBuilder, id: string, entity: T) => Observable<T>;
  deleteLink?: (ub: UrlBuilder, id: string, entity: T) => Observable<T>;
};

type CrudSpecConfig<T> = {
  serviceName: string;
  getService: () => CrudService<T>;
  getHttpMock: () => HttpTestingController;
  testDb: string;
  resource: string;
  id: string;
  altId: string;
  createEntity: (id: string) => T;
  getEntityId: (entity: T) => string;
  includeEmptyListTest?: boolean;
  includeGetOne404Test?: boolean;
  link?: LinkConfig;
  linkMethods?: LinkMethods;
};

export const setupHttpServiceTest = <T>(serviceType: Type<T>) => {
  let service: T;
  let httpMock: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [serviceType]
    });

    service = TestBed.inject(serviceType);
    httpMock = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    httpMock.verify();
  });

  return {
    getService: () => service,
    getHttpMock: () => httpMock
  };
};

export const describeCrudResourceService = <T>(config: CrudSpecConfig<T>) => {
  const {
    serviceName,
    getService,
    getHttpMock,
    testDb,
    resource,
    id,
    altId,
    createEntity,
    getEntityId,
    includeEmptyListTest,
    includeGetOne404Test,
    link,
    linkMethods
  } = config;

  describe('service creation', () => {
    it('should be created', () => {
      expect(getService()).toBeTruthy();
    });
  });

  describe('url()', () => {
    it(`should return ${serviceName} endpoint URL`, () => {
      const url = getService().url(testDb);
      expect(url).toBe(`/gedbrowserng/v1/dbs/${testDb}/${resource}`);
    });
  });

  describe('post()', () => {
    it('should create new entity', async () => {
      const entity = createEntity(id);

      const result$ = getService().post(testDb, entity);
      const promise = firstValueFrom(result$);

      const req = getHttpMock().expectOne(`/gedbrowserng/v1/dbs/${testDb}/${resource}`);
      expect(req.request.method).toBe('POST');
      expect(req.request.body).toEqual(entity);
      req.flush(entity);

      const result = await promise;
      expect(result).toEqual(entity);
    });
  });

  describe('getAll()', () => {
    it('should fetch all entities', async () => {
      const entities = [createEntity(id), createEntity(altId)];

      const result$ = getService().getAll(testDb);
      const promise = firstValueFrom(result$);

      const req = getHttpMock().expectOne(`/gedbrowserng/v1/dbs/${testDb}/${resource}`);
      expect(req.request.method).toBe('GET');
      req.flush(entities);

      const result = await promise;
      expect(result).toEqual(entities);
    });

    if (includeEmptyListTest) {
      it('should handle empty list', async () => {
        const result$ = getService().getAll(testDb);
        const promise = firstValueFrom(result$);

        const req = getHttpMock().expectOne(`/gedbrowserng/v1/dbs/${testDb}/${resource}`);
        req.flush([]);

        const result = await promise;
        expect(result).toEqual([]);
      });
    }
  });

  describe('getOne()', () => {
    it('should fetch single entity by ID', async () => {
      const entity = createEntity(id);

      const result$ = getService().getOne(testDb, id);
      const promise = firstValueFrom(result$);

      const req = getHttpMock().expectOne(`/gedbrowserng/v1/dbs/${testDb}/${resource}/${id}`);
      expect(req.request.method).toBe('GET');
      req.flush(entity);

      const result = await promise;
      expect(result).toEqual(entity);
    });

    if (includeGetOne404Test) {
      it('should handle 404 errors', async () => {
        const promise = firstValueFrom(getService().getOne(testDb, 'NOTFOUND'));

        const req = getHttpMock().expectOne(
          `/gedbrowserng/v1/dbs/${testDb}/${resource}/NOTFOUND`
        );
        req.flush('Not found', { status: 404, statusText: 'Not Found' });

        await expect(promise).rejects.toMatchObject({ status: 404 });
      });
    }
  });

  describe('put()', () => {
    it('should update existing entity', async () => {
      const entity = createEntity(id);

      const result$ = getService().put(testDb, entity);
      const promise = firstValueFrom(result$);

      const entityId = getEntityId(entity);
      const req = getHttpMock().expectOne(`/gedbrowserng/v1/dbs/${testDb}/${resource}/${entityId}`);
      expect(req.request.method).toBe('PUT');
      expect(req.request.body).toEqual(entity);
      req.flush(entity);

      const result = await promise;
      expect(result).toEqual(entity);
    });
  });

  describe('delete()', () => {
    it('should delete entity', async () => {
      const entity = createEntity(id);

      const result$ = getService().delete(testDb, entity);
      const promise = firstValueFrom(result$);

      const entityId = getEntityId(entity);
      const req = getHttpMock().expectOne(`/gedbrowserng/v1/dbs/${testDb}/${resource}/${entityId}`);
      expect(req.request.method).toBe('DELETE');
      req.flush(entity);

      const result = await promise;
      expect(result).toEqual(entity);
    });
  });

  if (link) {
    const methods: LinkMethods = { post: true, ...linkMethods };

    if (methods.post) {
      describe('postLink()', () => {
        it('should create linked resource', async () => {
          const ub = new UrlBuilder(testDb, resource, link.collection);
          const entity = createEntity(link.childId);

          const result$ = getService().postLink?.(ub, link.parentId, entity);
          const promise = result$ ? firstValueFrom(result$) : undefined;

          const req = getHttpMock().expectOne((r) =>
            r.url.includes(`/${resource}/${link.parentId}/${link.collection}`)
          );
          expect(req.request.method).toBe('POST');
          expect(req.request.body).toEqual(entity);
          req.flush(entity);

          const result = await promise;
          expect(result).toEqual(entity);
        });
      });
    }

    if (methods.put) {
      describe('putLink()', () => {
        it('should update linked resource', async () => {
          const ub = new UrlBuilder(testDb, resource, link.collection);
          const entity = createEntity(link.childId);

          const result$ = getService().putLink?.(ub, link.parentId, entity);
          const promise = result$ ? firstValueFrom(result$) : undefined;

          const req = getHttpMock().expectOne((r) =>
            r.url.includes(`/${resource}/${link.parentId}/${link.collection}`)
          );
          expect(req.request.method).toBe('PUT');
          expect(req.request.body).toEqual(entity);
          req.flush(entity);

          const result = await promise;
          expect(result).toEqual(entity);
        });
      });
    }

    if (methods.delete) {
      describe('deleteLink()', () => {
        it('should delete linked resource', async () => {
          const ub = new UrlBuilder(testDb, resource, link.collection);
          const entity = createEntity(link.childId);

          const result$ = getService().deleteLink?.(ub, link.parentId, entity);
          const promise = result$ ? firstValueFrom(result$) : undefined;

          const req = getHttpMock().expectOne((r) =>
            r.url.includes(`/${resource}/${link.parentId}/${link.collection}/${link.childId}`)
          );
          expect(req.request.method).toBe('DELETE');
          req.flush(entity);

          const result = await promise;
          expect(result).toEqual(entity);
        });
      });
    }
  }

  describe('baseUrl()', () => {
    it('should return base URL for database', () => {
      const url = getService().baseUrl(testDb);
      expect(url).toBe(`/gedbrowserng/v1/dbs/${testDb}`);
    });
  });
};
