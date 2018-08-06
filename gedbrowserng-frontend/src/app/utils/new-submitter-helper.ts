import { ApiSubmitter, ApiAttribute, NewSubmitterDialogData } from '../models';
import { AttributeDialogHelper } from './attribute-dialog-helper';

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
    submitter.attributes.push(adh.simpleAttribute('Name', name));
  }

  initNew(name: string): NewSubmitterDialogData {
    return { name: name };
  }
}
