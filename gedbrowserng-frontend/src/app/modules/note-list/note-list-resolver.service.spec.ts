import { NoteListResolverService } from './note-list-resolver.service';
import { NoteService } from '../../services';
import { setupResolverTest, describeResolverTests } from '../testing/resolver-spec-helpers';

describe('NoteListResolverService', () => {
  setupResolverTest(NoteListResolverService, NoteService);

  describeResolverTests('NoteListResolverService', NoteListResolverService, NoteService);
});
