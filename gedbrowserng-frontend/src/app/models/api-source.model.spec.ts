import { ApiSource } from './api-source.model';

describe('ApiSource Model', () => {
  it('should create an ApiSource instance', () => {
    const source = new ApiSource();
    expect(source).toBeTruthy();
  });

  it('should initialize title as empty string', () => {
    const source = new ApiSource();
    expect(source.title).toBe('');
  });

  it('should initialize images array', () => {
    const source = new ApiSource();
    expect(source.images).toEqual([]);
    expect(Array.isArray(source.images)).toBe(true);
  });

  it('should allow setting title', () => {
    const source = new ApiSource();
    source.title = 'Test Source Title';
    expect(source.title).toBe('Test Source Title');
  });

  it('should allow adding images', () => {
    const source = new ApiSource();
    const image = { string: 'image1', tail: 'image.jpg', attributes: [] };
    source.images.push(image as any);
    expect(source.images.length).toBe(1);
    expect(source.images[0]).toEqual(image);
  });

  it('should inherit from ApiObject', () => {
    const source = new ApiSource();
    source.string = 'S001';
    expect(source.string).toBe('S001');
  });
});
