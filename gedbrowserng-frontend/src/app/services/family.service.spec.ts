import { describe } from 'vitest';

import { FamilyService } from './family.service';
import { ApiFamily } from '../models';
import {
  describeCrudResourceService,
  setupHttpServiceTest
} from './testing/service-spec-helpers';

describe('FamilyService', () => {
  const testDb = 'testdb';
  const { getService, getHttpMock } = setupHttpServiceTest(FamilyService);
  const createFamily = (id: string) =>
    ({ string: id, dataset: testDb } as ApiFamily);

  describeCrudResourceService<ApiFamily>({
    serviceName: 'FamilyService',
    getService,
    getHttpMock,
    testDb,
    resource: 'families',
    id: 'F1',
    altId: 'F2',
    createEntity: createFamily,
    getEntityId: (entity) => entity.string,
    link: {
      parentId: 'F1',
      childId: 'F2',
      collection: 'spouses'
    },
    linkMethods: {
      post: true,
      put: true,
      delete: true
    }
  });
});
