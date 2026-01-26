import { SelectItem } from './select-item';

describe('SelectItem Model', () => {
  it('should create an instance', () => {
    const item = new SelectItem();
    expect(item).toBeTruthy();
  });

  it('should allow setting value', () => {
    const item = new SelectItem();
    item.value = 'test-value';
    expect(item.value).toBe('test-value');
  });

  it('should allow setting label', () => {
    const item = new SelectItem();
    item.label = 'Test Label';
    expect(item.label).toBe('Test Label');
  });

  it('should allow setting both value and label', () => {
    const item = new SelectItem();
    item.value = 'val1';
    item.label = 'Label 1';
    expect(item.value).toBe('val1');
    expect(item.label).toBe('Label 1');
  });
});
