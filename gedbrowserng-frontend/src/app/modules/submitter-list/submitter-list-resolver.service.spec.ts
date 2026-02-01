import { SubmitterListResolverService } from './submitter-list-resolver.service';
import { SubmitterService } from '../../services';
import { setupResolverTest, describeResolverTests } from '../testing/resolver-spec-helpers';

describe('SubmitterListResolverService', () => {
  setupResolverTest(SubmitterListResolverService, SubmitterService);

  describeResolverTests('SubmitterListResolverService', SubmitterListResolverService, SubmitterService);
});
