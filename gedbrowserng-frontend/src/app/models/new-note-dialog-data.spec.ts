import { NewNoteDialogData } from './new-note-dialog-data';
import {
  describeDialogDataInstantiation,
  describeTextProperty
} from './testing/dialog-data-spec-helpers';

describe('NewNoteDialogData Model', () => {
  describeDialogDataInstantiation('NewNoteDialogData', NewNoteDialogData);
  describeTextProperty(NewNoteDialogData);
});
