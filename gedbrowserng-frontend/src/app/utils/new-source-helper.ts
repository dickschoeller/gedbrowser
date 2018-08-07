import { ApiSource, ApiAttribute, NewSourceDialogData } from '../models';
import { AttributeDialogHelper } from './attribute-dialog-helper';
import { StringUtil } from './string-util';

export class NewSourceHelper {
  public static buildSource(data: NewSourceDialogData): ApiSource {
    if (StringUtil.isEmpty(data.title)) {
      data.title = NewSourceHelper.defaultTitle(data);
    }
    const source: ApiSource = new ApiSource();
    source.attributes = new Array<ApiAttribute>();
    NewSourceHelper.addTitle(data.title, source);
    NewSourceHelper.addAbbreviation(data.abbreviation, source);
    NewSourceHelper.addText(data.text, source);
    return source;
  }

  private static defaultTitle(data: NewSourceDialogData): string {
    return 'Person source';
  }

  private static addTitle(title: string, source: ApiSource) {
    const adh: AttributeDialogHelper = new AttributeDialogHelper(source);
    source.attributes.push(adh.simpleAttribute('Title', title));
  }

  private static addAbbreviation(abbreviation: string, source: ApiSource) {
    const adh: AttributeDialogHelper = new AttributeDialogHelper(source);
    source.attributes.push(adh.simpleAttribute('Abbreviation', abbreviation));
  }

  private static addText(text: string, source: ApiSource) {
    const adh: AttributeDialogHelper = new AttributeDialogHelper(source);
    source.attributes.push(adh.simpleAttribute('Text', text));
  }

  public static config(dataIn) {
    return { data: dataIn };
  }

  public static initNew(title: string): NewSourceDialogData {
    return { title: title, abbreviation: title, text: '' };
  }

  private static empty(result): boolean {
    return (result === null || result === undefined);
  }
}
