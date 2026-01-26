import { NewAttributeDialogData } from './new-attribute-dialog-data';
import { SelectItem } from './select-item';

describe('NewAttributeDialogData Model', () => {
  it('should create an instance', () => {
    const data = new NewAttributeDialogData();
    expect(data).toBeTruthy();
  });

  it('should allow setting options', () => {
    const data = new NewAttributeDialogData();
    const options: SelectItem[] = [
      { value: 'opt1', label: 'Option 1' },
      { value: 'opt2', label: 'Option 2' }
    ];
    data.options = options;
    expect(data.options).toEqual(options);
    expect(data.options.length).toBe(2);
  });

  it('should allow setting default', () => {
    const data = new NewAttributeDialogData();
    const defaultData: any = {
      insert: true,
      index: 0,
      type: 'BIRT',
      text: '',
      date: '',
      place: '',
      note: ''
    };
    data.default = defaultData;
    expect(data.default).toEqual(defaultData);
  });
});
