import { NewSubmitterDialogData } from './new-submitter-dialog-data';
import {
  describeDialogDataInstantiation,
  describeNameProperty
} from './testing/dialog-data-spec-helpers';

describe('NewSubmitterDialogData Model', () => {
  describeDialogDataInstantiation('NewSubmitterDialogData', NewSubmitterDialogData);
  describeNameProperty(NewSubmitterDialogData);
});
