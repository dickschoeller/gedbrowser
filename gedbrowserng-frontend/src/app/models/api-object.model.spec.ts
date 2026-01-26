import { ApiObject } from './api-object.model';

describe('ApiObject Model', () => {
  it('should create an ApiObject instance', () => {
    const obj = new ApiObject();
    expect(obj).toBeTruthy();
  });

  it('should initialize type as empty string', () => {
    const obj = new ApiObject();
    expect(obj.type).toBe('');
  });

  it('should initialize attributes array', () => {
    const obj = new ApiObject();
    expect(obj.attributes).toEqual([]);
    expect(Array.isArray(obj.attributes)).toBe(true);
  });

  it('should allow setting type', () => {
    const obj = new ApiObject();
    obj.type = 'PERSON';
    expect(obj.type).toBe('PERSON');
  });

  it('should allow setting string', () => {
    const obj = new ApiObject();
    obj.string = 'OBJ001';
    expect(obj.string).toBe('OBJ001');
  });

  it('should allow adding attributes', () => {
    const obj = new ApiObject();
    const attr = { string: 'attr1', tail: 'Attribute', type: '', attributes: [] };
    obj.attributes.push(attr as any);
    expect(obj.attributes.length).toBe(1);
    expect(obj.attributes[0]).toEqual(attr);
  });
});
