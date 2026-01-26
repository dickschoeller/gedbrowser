import { NewSubmitterDialogData } from './new-submitter-dialog-data';

describe('NewSubmitterDialogData Model', () => {
  it('should create an instance', () => {
    const data = new NewSubmitterDialogData();
    expect(data).toBeTruthy();
  });

  it('should allow setting name', () => {
    const data = new NewSubmitterDialogData();
    data.name = 'John Submitter';
    expect(data.name).toBe('John Submitter');
  });

  it('should handle empty name', () => {
    const data = new NewSubmitterDialogData();
    data.name = '';
    expect(data.name).toBe('');
  });

  it('should handle long names', () => {
    const data = new NewSubmitterDialogData();
    data.name = 'A Very Long Submitter Name With Many Words';
    expect(data.name).toBe('A Very Long Submitter Name With Many Words');
  });
});
