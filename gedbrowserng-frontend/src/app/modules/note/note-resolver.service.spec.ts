import { NoteResolverService } from './note-resolver.service';
import { NoteService } from '../../services';
import { setupResolverTest, describeResolverTests } from '../testing/resolver-spec-helpers';

describe('NoteResolverService', () => {
  setupResolverTest(NoteResolverService, NoteService);

  describeResolverTests('NoteResolverService', NoteResolverService, NoteService);
});
