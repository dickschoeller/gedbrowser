import { ApiAttribute } from './api-attribute.model';

describe('ApiAttribute Model', () => {
  it('should create an ApiAttribute instance', () => {
    const attribute = new ApiAttribute();
    expect(attribute).toBeTruthy();
  });

  it('should inherit from ApiTail', () => {
    const attribute = new ApiAttribute();
    attribute.string = 'A001';
    attribute.tail = 'test tail';
    expect(attribute.string).toBe('A001');
    expect(attribute.tail).toBe('test tail');
  });

  it('should have attributes array from ApiObject', () => {
    const attribute = new ApiAttribute();
    expect(Array.isArray(attribute.attributes)).toBe(true);
  });
});
