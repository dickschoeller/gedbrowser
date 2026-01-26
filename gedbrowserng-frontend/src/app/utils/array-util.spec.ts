import { ArrayUtil } from './array-util';

describe('ArrayUtil', () => {
  it('should return true for undefined array', () => {
    expect(ArrayUtil.isEmpty(undefined)).toBe(true);
  });

  it('should return true for null array', () => {
    expect(ArrayUtil.isEmpty(null)).toBe(true);
  });

  it('should return true for empty array', () => {
    expect(ArrayUtil.isEmpty([])).toBe(true);
  });

  it('should return false for array with elements', () => {
    expect(ArrayUtil.isEmpty([1, 2, 3])).toBe(false);
  });

  it('should return false for array with one element', () => {
    expect(ArrayUtil.isEmpty(['test'])).toBe(false);
  });

  it('should return false for array with objects', () => {
    const arr = [{ id: 1 }, { id: 2 }];
    expect(ArrayUtil.isEmpty(arr)).toBe(false);
  });
});
