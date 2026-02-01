import { NewSourceDialogData } from './new-source-dialog-data';
import {
  describeDialogDataInstantiation,
  describeSourceProperties
} from './testing/dialog-data-spec-helpers';

describe('NewSourceDialogData Model', () => {
  describeDialogDataInstantiation('NewSourceDialogData', NewSourceDialogData);
  describeSourceProperties(NewSourceDialogData);
});
