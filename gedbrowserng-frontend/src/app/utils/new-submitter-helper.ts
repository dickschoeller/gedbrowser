import { ApiSubmitter, ApiAttribute } from '../models';
import { AttributeDialogHelper } from '../components/attribute-dialog/attribute-dialog-helper';
import { NewSubmitterDialogData } from '../models/new-submitter-dialog-data';

export class NewSubmitterHelper {
  constructor() {}

  buildSubmitter(data: NewSubmitterDialogData): ApiSubmitter {
    if (data.name === '' || data.name === undefined || data.name === null) {
      data.name = 'Unknown';
    }
    const submitter: ApiSubmitter = new ApiSubmitter();
    submitter.attributes = new Array<ApiAttribute>();
    this.addName(data.name, submitter);
    return submitter;
  }

  addName(name, submitter) {
    const adh: AttributeDialogHelper = new AttributeDialogHelper(submitter);
    submitter.attributes.push(adh.populateNewAttribute({
      insert: true, index: 0,
      type: 'Name', text: name, date: '', place: '', note: '',
      originalType: 'Name', originalText: name, originalDate: '',
      originalPlace: '', originalNote: ''
    }));
  }

  initNew(name: string): NewSubmitterDialogData {
    return { name: name };
  }
}
