import { ApiSubmitter, ApiAttribute, NewSubmitterDialogData } from '../models';
import { AttributeDialogHelper } from './attribute-dialog-helper';
import { StringUtil } from './string-util';

export class NewSubmitterHelper {
  public static buildSubmitter(data: NewSubmitterDialogData): ApiSubmitter {
    if (StringUtil.isEmpty(data.name)) {
      data.name = 'Unknown';
    }
    const submitter: ApiSubmitter = new ApiSubmitter();
    submitter.attributes = new Array<ApiAttribute>();
    this.addName(data.name, submitter);
    return submitter;
  }

  private static addName(name, submitter) {
    const adh: AttributeDialogHelper = new AttributeDialogHelper(submitter);
    submitter.attributes.push(adh.simpleAttribute('Name', name));
  }

  public static initNew(name: string): NewSubmitterDialogData {
    return { name: name };
  }
}
