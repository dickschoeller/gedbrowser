import {ApiSource, ApiAttribute} from '../models';
import {AttributeDialogHelper} from '../components/attribute-dialog/attribute-dialog-helper';
import {NewSourceDialogData} from '../models/new-source-dialog-data';

export class NewSourceHelper {
  constructor() {}

  buildSource(data: NewSourceDialogData): ApiSource {
    if (data.title === '' || data.title === undefined || data.title === null) {
      data.title = this.defaultTitle(data);
    }
    const source: ApiSource = new ApiSource();
    source.attributes = new Array<ApiAttribute>();
    this.addTitle(data.title, source);
    this.addAbbreviation(data.abbreviation, source);
    this.addText(data.text, source);
    return source;
  }

  defaultTitle(data: NewSourceDialogData): string {
    return 'Person source';
  }

  addTitle(title: string, source: ApiSource) {
    const adh: AttributeDialogHelper = new AttributeDialogHelper(source);
    source.attributes.push(adh.simpleAttribute('Title', title));
  }

  addAbbreviation(abbreviation: string, source: ApiSource) {
    const adh: AttributeDialogHelper = new AttributeDialogHelper(source);
    source.attributes.push(adh.simpleAttribute('Abbreviation', abbreviation));
  }

  addText(text: string, source: ApiSource) {
    const adh: AttributeDialogHelper = new AttributeDialogHelper(source);
    source.attributes.push(adh.simpleAttribute('Text', text));
  }

  config(dataIn) {
    return {data: dataIn};
  }

  initNew(title: string): NewSourceDialogData {
    return {title: title, abbreviation: title, text: ''};
  }

  empty(result): boolean {
    return (result === null || result === undefined);
  }
}
