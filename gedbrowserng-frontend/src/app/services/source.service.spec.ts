import { describe } from 'vitest';

import { SourceService } from './source.service';
import { ApiSource } from '../models';
import {
  describeCrudResourceService,
  setupHttpServiceTest
} from './testing/service-spec-helpers';

describe('SourceService', () => {
  const testDb = 'testdb';
  const { getService, getHttpMock } = setupHttpServiceTest(SourceService);
  const createSource = (id: string) =>
    ({ string: id, dataset: testDb } as ApiSource);

  describeCrudResourceService<ApiSource>({
    serviceName: 'SourceService',
    getService,
    getHttpMock,
    testDb,
    resource: 'sources',
    id: 'S1',
    altId: 'S2',
    createEntity: createSource,
    link: {
      parentId: 'S1',
      childId: 'S2',
      collection: 'references'
    },
    linkMethods: {
      post: true
    }
  });
});
