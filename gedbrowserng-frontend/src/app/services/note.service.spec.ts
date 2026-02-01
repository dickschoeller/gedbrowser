import { describe } from 'vitest';

import { NoteService } from './note.service';
import { ApiNote } from '../models';
import {
  describeCrudResourceService,
  setupHttpServiceTest
} from './testing/service-spec-helpers';

describe('NoteService', () => {
  const testDb = 'testdb';
  const { getService, getHttpMock } = setupHttpServiceTest(NoteService);
  const createNote = (id: string) => ({ string: id, dataset: testDb } as ApiNote);

  describeCrudResourceService<ApiNote>({
    serviceName: 'NoteService',
    getService,
    getHttpMock,
    testDb,
    resource: 'notes',
    id: 'N1',
    altId: 'N2',
    createEntity: createNote,
    link: {
      parentId: 'N1',
      childId: 'N2',
      collection: 'references'
    },
    linkMethods: {
      post: true
    }
  });
});
