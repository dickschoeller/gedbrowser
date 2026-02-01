import { SubmitterResolverService } from './submitter-resolver.service';
import { SubmitterService } from '../../services';
import { setupResolverTest, describeResolverTests } from '../testing/resolver-spec-helpers';

describe('SubmitterResolverService', () => {
  setupResolverTest(SubmitterResolverService, SubmitterService);

  describeResolverTests('SubmitterResolverService', SubmitterResolverService, SubmitterService);
});
