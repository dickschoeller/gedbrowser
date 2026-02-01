import { PersonListResolverService } from './person-list-resolver.service';
import { PersonService } from '../../services';
import { setupResolverTest, describeResolverTests } from '../testing/resolver-spec-helpers';

describe('PersonListResolverService', () => {
  setupResolverTest(PersonListResolverService, PersonService);

  describeResolverTests('PersonListResolverService', PersonListResolverService, PersonService);
});
