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
  const defaultPoster = 'data:image/gif;base64,R0lGODlhAQABAIAAAAAAAP///ywAAAAAAQABAAACAUwAOw==';

  const parseVideoData = (videoData?: string) => JSON.parse(videoData || '{}');

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

  describe('isVideo', () => {
    it.each(['mp4', 'mov', 'avi', 'm4v', 'mpg', 'webm'])(
      'should identify %s as video',
      (extension) => {
        const attr = createAttribute(`clip.${extension}`);
        expect(ImageUtil.isVideo(attr)).toBe(true);
      }
    );

    it('should not identify non-video file', () => {
      const attr = createAttribute('document.pdf');
      expect(ImageUtil.isVideo(attr)).toBe(false);
    });

    it('should identify extension without dot suffix', () => {
      const attr = createAttribute('movie_mpeg');
      expect(ImageUtil.isVideo(attr)).toBe(true);
    });
  });

  describe('isYouTube', () => {
    it('should identify full YouTube URL', () => {
      const attr = createAttribute('https://www.youtube.com/watch?v=dQw4w9WgXcQ');
      expect(ImageUtil.isYouTube(attr)).toBe(true);
    });

    it('should identify short YouTube URL', () => {
      const attr = createAttribute('https://youtu.be/dQw4w9WgXcQ');
      expect(ImageUtil.isYouTube(attr)).toBe(true);
    });

    it('should return false for non-YouTube URLs', () => {
      const attr = createAttribute('https://example.org/video.mp4');
      expect(ImageUtil.isYouTube(attr)).toBe(false);
    });
  });

  describe('imageFormat', () => {
    it.each([
      ['photo.jpg', 'jpg'],
      ['photo.png', 'png']
    ])('should return %s format for %s', (fileName, expected) => {
      const attr = createAttribute(fileName);
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

    it('should return true for wrapper containing video', () => {
      const innerVideo = createAttribute('clip.mp4');
      const wrapper = createWrapper(innerVideo);
      expect(ImageUtil.isImageWrapper(wrapper)).toBe(true);
    });

    it('should return true for wrapper containing YouTube URL', () => {
      const innerYouTube = createAttribute('https://www.youtube.com/watch?v=dQw4w9WgXcQ');
      const wrapper = createWrapper(innerYouTube);
      expect(ImageUtil.isImageWrapper(wrapper)).toBe(true);
    });

    it('should recurse through nested wrappers', () => {
      const deepImage = createAttribute('deep.png');
      const nestedWrapper = createAttribute('inner-wrapper', [deepImage]);
      const outerWrapper = createAttribute('outer-wrapper', [nestedWrapper]);
      expect(ImageUtil.isImageWrapper(outerWrapper)).toBe(true);
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

    it('should convert wrapper with video to gallery images', () => {
      const innerVideo = createAttribute('clip.mp4');
      const wrapper = createWrapper(innerVideo);
      const result = ImageUtil.galleryImages([wrapper]);
      expect(result.length).toBe(1);
      expect(result[0].mediaType).toBe('video');
      expect(result[0].videoData).toContain('video/mp4');
    });

    it('should include title from sibling attribute as description', () => {
      const title = createAttribute('Vacation 2024');
      title.string = 'Title';
      const media = createAttribute('clip.mp4');
      const wrapper = createAttribute('wrapper', [title, media]);
      const result = ImageUtil.galleryImages([wrapper]);
      expect(result[0].description).toBe('Vacation 2024');
    });

    it('should return empty result if wrapper has no media at any depth', () => {
      const nestedNoMedia = createAttribute('nested', [createAttribute('notes.txt')]);
      const wrapper = createAttribute('wrapper', [createAttribute('readme.md'), nestedNoMedia]);
      expect(ImageUtil.galleryImages([wrapper])).toEqual([]);
    });
  });

  describe('galleryImage', () => {
    it('should build direct image gallery item', () => {
      const image = ImageUtil.galleryImage(createAttribute('photo.jpeg'));
      expect(image.mediaType).toBe('image');
      expect(image.small).toBe('photo.jpeg');
      expect(image.url).toBe('photo.jpeg');
      expect(image.videoData).toBeUndefined();
    });

    it('should build direct YouTube gallery item with poster', () => {
      const youtube = ImageUtil.galleryImage(createAttribute('https://youtu.be/dQw4w9WgXcQ?t=4'));
      expect(youtube.mediaType).toBe('youtube');
      expect(youtube.url).toContain('youtu.be');
      expect(youtube.small).toBe('https://img.youtube.com/vi/dQw4w9WgXcQ/hqdefault.jpg');
    });

    it('should parse v parameter from full YouTube URL for poster', () => {
      const youtube = ImageUtil.galleryImage(createAttribute('https://www.youtube.com/watch?v=dQw4w9WgXcQ&ab_channel=test'));
      expect(youtube.mediaType).toBe('youtube');
      expect(youtube.small).toBe('https://img.youtube.com/vi/dQw4w9WgXcQ/hqdefault.jpg');
    });

    it('should build direct video gallery item with default poster', () => {
      const video = ImageUtil.galleryImage(createAttribute('direct.mp4'));
      expect(video.mediaType).toBe('video');
      expect(video.small).toBe(defaultPoster);
      expect(parseVideoData(video.videoData).source[0].type).toBe('video/mp4');
    });

    it('should fallback to default poster for unparseable YouTube ID', () => {
      const youtube = ImageUtil.galleryImage(createAttribute('https://www.youtube.com/watch?feature=share'));
      expect(youtube.mediaType).toBe('youtube');
      expect(youtube.small).toBe(defaultPoster);
    });

    it.each([
      ['clip.webm', 'video/webm'],
      ['clip.avi', 'video/x-msvideo'],
      ['clip.mov', 'video/quicktime'],
      ['clip.m4v', 'video/x-m4v'],
      ['clip.mpg', 'video/mpeg'],
      ['clip.mpeg', 'video/mpeg'],
      ['clip.mp4', 'video/mp4'],
      ['clipmp4', 'video/mp4']
    ])('should set mime type for %s videos to %s', (fileName, expectedMime) => {
      const wrapper = createWrapper(createAttribute(fileName));
      const image = ImageUtil.galleryImages([wrapper])[0];
      const parsed = parseVideoData(image.videoData);
      expect(image.mediaType).toBe('video');
      expect(parsed.source[0].type).toBe(expectedMime);
      expect(parsed.attributes.controls).toBe(true);
      expect(parsed.attributes.preload).toBe(false);
      expect(parsed.attributes.playsinline).toBe(true);
    });

    it('should use nested poster image when wrapper contains video and image', () => {
      const poster = createAttribute('poster.png');
      const video = createAttribute('movie.mp4');
      const wrapper = createAttribute('wrapper', [video, createAttribute('nested', [poster])]);
      const image = ImageUtil.galleryImage(wrapper);
      expect(image.mediaType).toBe('video');
      expect(image.small).toBe('poster.png');
    });

    it('should resolve YouTube media from nested attributes', () => {
      const youtube = createAttribute('https://www.youtube.com/watch?v=abc123xyz');
      const wrapper = createAttribute('wrapper', [youtube]);
      const image = ImageUtil.galleryImage(wrapper);
      expect(image.mediaType).toBe('youtube');
      expect(image.small).toBe('https://img.youtube.com/vi/abc123xyz/hqdefault.jpg');
    });

    it('should recurse to nested media wrapper to resolve image', () => {
      const deepImage = createAttribute('deep.jpg');
      const nestedWrapper = createAttribute('nested', [deepImage]);
      const wrapper = createAttribute('wrapper', [nestedWrapper]);
      const image = ImageUtil.galleryImage(wrapper);
      expect(image.mediaType).toBe('image');
      expect(image.url).toBe('deep.jpg');
    });

    it('should return null for wrapper with no supported media', () => {
      const wrapper = createAttribute('wrapper', [createAttribute('readme.md')]);
      expect(ImageUtil.galleryImage(wrapper)).toBeNull();
    });
  });
});
