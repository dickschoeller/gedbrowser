import { ApiFamily } from './api-family.model';

describe('ApiFamily Model', () => {
  it('should create an ApiFamily instance', () => {
    const family = new ApiFamily();
    expect(family).toBeTruthy();
  });

  it('should initialize spouses array', () => {
    const family = new ApiFamily();
    expect(family.spouses).toEqual([]);
    expect(Array.isArray(family.spouses)).toBe(true);
  });

  it('should initialize children array', () => {
    const family = new ApiFamily();
    expect(family.children).toEqual([]);
    expect(Array.isArray(family.children)).toBe(true);
  });

  it('should initialize images array', () => {
    const family = new ApiFamily();
    expect(family.images).toEqual([]);
    expect(Array.isArray(family.images)).toBe(true);
  });

  it('should allow adding spouses', () => {
    const family = new ApiFamily();
    const spouse = { string: 'spouse1', tail: 'Spouse Name', attributes: [] };
    family.spouses.push(spouse as any);
    expect(family.spouses.length).toBe(1);
    expect(family.spouses[0]).toEqual(spouse);
  });

  it('should allow adding children', () => {
    const family = new ApiFamily();
    const child = { string: 'child1', tail: 'Child Name', attributes: [] };
    family.children.push(child as any);
    expect(family.children.length).toBe(1);
    expect(family.children[0]).toEqual(child);
  });

  it('should allow adding images', () => {
    const family = new ApiFamily();
    const image = { string: 'image1', tail: 'image.jpg', attributes: [] };
    family.images.push(image as any);
    expect(family.images.length).toBe(1);
    expect(family.images[0]).toEqual(image);
  });

  it('should inherit from ApiObject', () => {
    const family = new ApiFamily();
    expect(family.string).toBeUndefined();
    family.string = 'F001';
    expect(family.string).toBe('F001');
  });
});
