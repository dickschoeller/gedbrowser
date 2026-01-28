import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { TestBed } from '@angular/core/testing';
import { describe, it, expect, beforeEach, afterEach } from 'vitest';

import { NoteService } from './note.service';
import { ApiNote } from '../models';
import { UrlBuilder } from '../utils/urlbuilder';

describe('NoteService', () => {
  let service: NoteService;
  let httpMock: HttpTestingController;
  const testDb = 'testdb';

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [NoteService]
    });
    service = TestBed.inject(NoteService);
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
    it('should return notes endpoint URL', () => {
      const url = service.url(testDb);
      expect(url).toBe('/gedbrowserng/v1/dbs/testdb/notes');
    });
  });

  describe('post()', () => {
    it('should create new note', async () => {
      const newNote: ApiNote = {
        string: 'N1',
        dataset: testDb
      } as ApiNote;

      const result$ = service.post(testDb, newNote);
      const promise = result$.toPromise();

      const req = httpMock.expectOne('/gedbrowserng/v1/dbs/testdb/notes');
      expect(req.request.method).toBe('POST');
      req.flush(newNote);

      const result = await promise;
      expect(result).toEqual(newNote);
    });
  });

  describe('getAll()', () => {
    it('should fetch all notes', async () => {
      const notes: ApiNote[] = [
        { string: 'N1', dataset: testDb } as ApiNote,
        { string: 'N2', dataset: testDb } as ApiNote
      ];

      const result$ = service.getAll(testDb);
      const promise = result$.toPromise();

      const req = httpMock.expectOne('/gedbrowserng/v1/dbs/testdb/notes');
      req.flush(notes);

      const result = await promise;
      expect(result).toEqual(notes);
    });
  });

  describe('getOne()', () => {
    it('should fetch single note by ID', async () => {
      const note: ApiNote = {
        string: 'N1',
        dataset: testDb
      } as ApiNote;

      const result$ = service.getOne(testDb, 'N1');
      const promise = result$.toPromise();

      const req = httpMock.expectOne('/gedbrowserng/v1/dbs/testdb/notes/N1');
      req.flush(note);

      const result = await promise;
      expect(result).toEqual(note);
    });
  });

  describe('put()', () => {
    it('should update existing note', async () => {
      const note: ApiNote = {
        string: 'N1',
        dataset: testDb
      } as ApiNote;

      const result$ = service.put(testDb, note);
      const promise = result$.toPromise();

      const req = httpMock.expectOne('/gedbrowserng/v1/dbs/testdb/notes/N1');
      expect(req.request.method).toBe('PUT');
      req.flush(note);

      const result = await promise;
      expect(result).toEqual(note);
    });
  });

  describe('delete()', () => {
    it('should delete note', async () => {
      const note: ApiNote = {
        string: 'N1',
        dataset: testDb
      } as ApiNote;

      const result$ = service.delete(testDb, note);
      const promise = result$.toPromise();

      const req = httpMock.expectOne('/gedbrowserng/v1/dbs/testdb/notes/N1');
      expect(req.request.method).toBe('DELETE');
      req.flush(note);

      const result = await promise;
      expect(result).toEqual(note);
    });
  });

  describe('postLink()', () => {
    it('should create linked resource', async () => {
      const ub = new UrlBuilder(testDb, 'notes', 'references');
      const note: ApiNote = {
        string: 'N2',
        dataset: testDb
      } as ApiNote;

      const result$ = service.postLink(ub, 'N1', note);
      const promise = result$.toPromise();

      const req = httpMock.expectOne((r) => r.url.includes('/notes/N1/references'));
      expect(req.request.method).toBe('POST');
      req.flush(note);

      const result = await promise;
      expect(result).toEqual(note);
    });
  });

  describe('baseUrl()', () => {
    it('should return base URL for database', () => {
      const url = service.baseUrl(testDb);
      expect(url).toBe('/gedbrowserng/v1/dbs/testdb');
    });
  });
});
