import { ApiNote } from './api-note.model';
import {
  describeModelInstantiation,
  describeApiTailInheritance
} from './testing/api-model-spec-helpers';

describe('ApiNote Model', () => {
  describeModelInstantiation('ApiNote', ApiNote);
  describeApiTailInheritance(ApiNote, 'tail');
});
