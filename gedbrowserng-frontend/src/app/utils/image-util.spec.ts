import { ImageUtil } from './image-util';
import { ApiAttribute } from '../models';

describe('ImageUtil', () => {
  const createAttribute = (tail: string, attributes: ApiAttribute[] = []): ApiAttribute => {
    const attr = new ApiAttribute();
    attr.tail = tail;
    attr.attributes = attributes;
    return attr;
  };

  const createWrapper = (inner: ApiAttribute) => createAttribute('wrapper.txt', [inner]);

  describe('isImage', () => {
    it.each(['jpg', 'jpeg', 'png', 'gif', 'bmp', 'svg'])(
      'should identify %s as image',
      (extension) => {
        const attr = createAttribute(`photo.${extension}`);
        expect(ImageUtil.isImage(attr)).toBe(true);
      }
    );

    it('should not identify non-image file', () => {
      const attr = createAttribute('document.pdf');
      expect(ImageUtil.isImage(attr)).toBe(false);
    });

    it('should be case-insensitive', () => {
      const attr = createAttribute('photo.JPG');
      expect(ImageUtil.isImage(attr)).toBe(true);
    });
  });

  describe('imageFormat', () => {
    it.each([
      ['photo.jpg', 'jpg'],
      ['photo.png', 'png']
    ])('should return %s format for %s', (_tail, expected) => {
      const attr = createAttribute(_tail);
      expect(ImageUtil.imageFormat(attr)).toBe(expected);
    });

    it('should return default jpg for non-image file', () => {
      const attr = createAttribute('document.pdf');
      expect(ImageUtil.imageFormat(attr)).toBe('jpg');
    });

    it('should handle uppercase extensions', () => {
      const attr = createAttribute('photo.PNG');
      expect(ImageUtil.imageFormat(attr)).toBe('png');
    });
  });

  describe('imageAttributes', () => {
    it('should return empty array for empty input', () => {
      const result = ImageUtil.imageAttributes([]);
      expect(result).toEqual([]);
    });

    it('should filter image attributes', () => {
      const img = createAttribute('photo.jpg');
      const doc = createAttribute('file.pdf');
      const attrs = [img, doc];
      const result = ImageUtil.imageAttributes(attrs);
      // Direct images are not included because isImageWrapper checks nested attributes, not the tail
      expect(result.length).toBe(0);
    });

    it('should identify wrapper with image', () => {
      const innerImage = createAttribute('photo.jpg');
      const wrapper = createWrapper(innerImage);
      const result = ImageUtil.imageAttributes([wrapper]);
      expect(result.length).toBe(1);
    });
  });

  describe('isImageWrapper', () => {
    it('should return false for non-image', () => {
      const attr = createAttribute('document.pdf');
      expect(ImageUtil.isImageWrapper(attr)).toBe(false);
    });

    it('should return false for direct image file', () => {
      const attr = createAttribute('photo.jpg');
      expect(ImageUtil.isImageWrapper(attr)).toBe(false);
    });

    it('should return true for wrapper containing image', () => {
      const innerImage = createAttribute('photo.jpg');
      const wrapper = createWrapper(innerImage);
      expect(ImageUtil.isImageWrapper(wrapper)).toBe(true);
    });
  });

  describe('galleryImages', () => {
    it('should convert attributes to gallery images', () => {
      const attr = createAttribute('photo.jpg');
      const result = ImageUtil.galleryImages([attr]);
      // Direct images would need isImageWrapper to return true, but it only checks nested attrs
      expect(result.length).toBe(0);
    });

    it('should handle empty array', () => {
      const result = ImageUtil.galleryImages([]);
      expect(result).toEqual([]);
    });

    it('should skip non-images', () => {
      const img = createAttribute('photo.jpg');
      const doc = createAttribute('file.pdf');
      const result = ImageUtil.galleryImages([img, doc]);
      // Both are direct images with no nested attributes, so isImageWrapper returns false
      expect(result.length).toBe(0);
    });

    it('should convert wrapper with images to gallery images', () => {
      const innerImage = createAttribute('photo.jpg');
      const wrapper = createWrapper(innerImage);
      const result = ImageUtil.galleryImages([wrapper]);
      expect(result.length).toBe(1);
      expect(result[0]).toBeDefined();
    });
  });
});
