import {ApiSource, ApiAttribute} from '../../models';
import {AttributeDialogHelper} from '../attribute-dialog/attribute-dialog-helper';
import {NewSourceDialogData} from './new-source-dialog-data';

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
    source.attributes.push(adh.populateNewAttribute({
      insert: true, index: 0,
      type: 'Title', text: title, date: '', place: '', note: '',
      originalType: 'Title', originalText: title, originalDate: '',
      originalPlace: '', originalNote: ''
    }));
  }

  addAbbreviation(abbreviation: string, source: ApiSource) {
    const adh: AttributeDialogHelper = new AttributeDialogHelper(source);
    source.attributes.push(adh.populateNewAttribute({
      insert: true, index: 0,
      type: 'Abbreviation', text: abbreviation, date: '', place: '', note: '',
      originalType: 'Abbreviation', originalText: abbreviation, originalDate: '',
      originalPlace: '', originalNote: ''
    }));
  }

  addText(text: string, source: ApiSource) {
    const adh: AttributeDialogHelper = new AttributeDialogHelper(source);
    source.attributes.push(adh.populateNewAttribute({
      insert: true, index: 0,
      type: 'Text', text: text, date: '', place: '', note: '',
      originalType: 'Text', originalText: text, originalDate: '',
      originalPlace: '', originalNote: ''
    }));
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
