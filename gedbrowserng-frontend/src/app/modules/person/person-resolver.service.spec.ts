import { PersonResolverService } from './person-resolver.service';
import { PersonService } from '../../services';
import { setupResolverTest, describeResolverTests } from '../testing/resolver-spec-helpers';

describe('PersonResolverService', () => {
  setupResolverTest(PersonResolverService, PersonService);

  describeResolverTests('PersonResolverService', PersonResolverService, PersonService, {
    testResolve: true,
    routeParams: { dataset: 'ds', id: 'P1' },
    stateUrl: '/ds/persons/P1'
  });
});
