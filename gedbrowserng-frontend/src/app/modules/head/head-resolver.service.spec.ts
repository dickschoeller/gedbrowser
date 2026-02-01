import { HeadResolverService } from './head-resolver.service';
import { HeadService } from '../../services';
import { setupResolverTest, describeResolverTests } from '../testing/resolver-spec-helpers';

describe('HeadResolverService', () => {
  setupResolverTest(HeadResolverService, HeadService);

  describeResolverTests('HeadResolverService', HeadResolverService, HeadService);
});
