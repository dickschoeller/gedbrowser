import { NewSourceDialogData } from './new-source-dialog-data';

describe('NewSourceDialogData Model', () => {
  it('should create an instance', () => {
    const data = new NewSourceDialogData();
    expect(data).toBeTruthy();
  });

  it('should allow setting title', () => {
    const data = new NewSourceDialogData();
    data.title = 'Source Title';
    expect(data.title).toBe('Source Title');
  });

  it('should allow setting abbreviation', () => {
    const data = new NewSourceDialogData();
    data.abbreviation = 'ST';
    expect(data.abbreviation).toBe('ST');
  });

  it('should allow setting text', () => {
    const data = new NewSourceDialogData();
    data.text = 'Source text content';
    expect(data.text).toBe('Source text content');
  });

  it('should allow setting all properties at once', () => {
    const data = new NewSourceDialogData();
    data.title = 'Census Record';
    data.abbreviation = 'CENSUS';
    data.text = '1900 US Census';

    expect(data.title).toBe('Census Record');
    expect(data.abbreviation).toBe('CENSUS');
    expect(data.text).toBe('1900 US Census');
  });
});
