import { SourceResolverService } from './source-resolver.service';
import { SourceService } from '../../services';
import { setupResolverTest, describeResolverTests } from '../testing/resolver-spec-helpers';

describe('SourceResolverService', () => {
  setupResolverTest(SourceResolverService, SourceService);

  describeResolverTests('SourceResolverService', SourceResolverService, SourceService);
});
