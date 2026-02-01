import { describe } from 'vitest';

import { PersonService } from './person.service';
import { ApiPerson } from '../models';
import {
  describeCrudResourceService,
  setupHttpServiceTest
} from './testing/service-spec-helpers';

describe('PersonService', () => {
  const testDb = 'testdb';
  const { getService, getHttpMock } = setupHttpServiceTest(PersonService);
  const createPerson = (id: string) =>
    ({ string: id, dataset: testDb } as ApiPerson);

  describeCrudResourceService<ApiPerson>({
    serviceName: 'PersonService',
    getService,
    getHttpMock,
    testDb,
    resource: 'persons',
    id: 'I1',
    altId: 'I2',
    createEntity: createPerson,
    getEntityId: (entity) => entity.string,
    includeEmptyListTest: true,
    includeGetOne404Test: true,
    link: {
      parentId: 'I1',
      childId: 'I2',
      collection: 'children'
    },
    linkMethods: {
      post: true,
      put: true,
      delete: true
    }
  });
});
