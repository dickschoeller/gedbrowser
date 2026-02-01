import { describe } from 'vitest';

import { SubmitterService } from './submitter.service';
import { ApiSubmitter } from '../models';
import {
  describeCrudResourceService,
  setupHttpServiceTest
} from './testing/service-spec-helpers';

describe('SubmitterService', () => {
  const testDb = 'testdb';
  const { getService, getHttpMock } = setupHttpServiceTest(SubmitterService);
  const createSubmitter = (id: string) =>
    ({ string: id, dataset: testDb } as ApiSubmitter);

  describeCrudResourceService<ApiSubmitter>({
    serviceName: 'SubmitterService',
    getService,
    getHttpMock,
    testDb,
    resource: 'submitters',
    id: 'SUBM1',
    altId: 'SUBM2',
    createEntity: createSubmitter,
    getEntityId: (entity) => entity.string,
    link: {
      parentId: 'SUBM1',
      childId: 'SUBM2',
      collection: 'submissions'
    },
    linkMethods: {
      post: true
    }
  });
});
