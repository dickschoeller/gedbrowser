import { SourceListResolverService } from './source-list-resolver.service';
import { SourceService } from '../../services';
import { setupResolverTest, describeResolverTests } from '../testing/resolver-spec-helpers';

describe('SourceListResolverService', () => {
  setupResolverTest(SourceListResolverService, SourceService);

  describeResolverTests('SourceListResolverService', SourceListResolverService, SourceService);
});
