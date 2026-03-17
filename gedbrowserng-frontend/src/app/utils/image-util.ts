import { ApiAttribute } from '../models';

export interface GalleryImage {
  small: string;
  medium: string;
  big: string;
  description: string;
  url: string;
  mediaType?: 'image' | 'video' | 'youtube';
  videoData?: string;
}

export class ImageUtil {
  constructor() {}

  public static imageAttributes(attributes: Array<ApiAttribute>): Array<ApiAttribute> {
    const images: Array<ApiAttribute> = new Array<ApiAttribute>();
    for (const attribute of attributes) {
      if (this.isMediaWrapper(attribute)) {
        images.push(attribute);
      }
    }
    return images;
  }

  public static galleryImages(attributes: Array<ApiAttribute>): Array<GalleryImage> {
    const gallery: Array<GalleryImage> = new Array<GalleryImage>();
    for (const attribute of this.imageAttributes(attributes)) {
      gallery.push(this.galleryImage(attribute));
    }
    return gallery;
  }

  public static isImageWrapper(attr: ApiAttribute): boolean {
    return this.isMediaWrapper(attr);
  }

  public static isMediaWrapper(attr: ApiAttribute): boolean {
    for (const attribute of attr.attributes) {
      if (this.isImage(attribute) || this.isVideo(attribute) || this.isYouTube(attribute) || this.isMediaWrapper(attribute)) {
        return true;
      }
    }
    return false;
  }

  public static isImage(attribute: ApiAttribute): boolean {
    const types = [ 'bmp', 'gif', 'ico', 'jpg', 'jpeg', 'png', 'tiff', 'tif', 'svg' ];
    const tail = (attribute.tail || '').toLowerCase();
    for (const t of types) {
      if (tail.endsWith(`.${t}`) || tail.endsWith(t)) {
        return true;
      }
    }
    return false;
  }

  public static isVideo(attribute: ApiAttribute): boolean {
    const types = [ 'avi', 'm4v', 'mov', 'mp4', 'mpg', 'mpeg', 'webm' ];
    const tail = (attribute.tail || '').toLowerCase();
    for (const t of types) {
      if (tail.endsWith(`.${t}`) || tail.endsWith(t)) {
        return true;
      }
    }
    return false;
  }

  public static isYouTube(attribute: ApiAttribute): boolean {
    const tail = (attribute.tail || '').toLowerCase();
    return tail.includes('youtube.com/watch') || tail.includes('youtu.be/');
  }

  public static imageFormat(attribute: ApiAttribute): string {
    const types = [ 'bmp', 'gif', 'ico', 'jpg', 'jpeg', 'png', 'tiff', 'tif', 'svg' ];
    for (const t of types) {
      if (attribute.tail.toLowerCase().endsWith(t)) {
        return t;
      }
    }
    return 'jpg';
  }

  public static galleryImage(attr: ApiAttribute): GalleryImage {
    const description = 'Media';
    if (this.isYouTube(attr)) {
      return this.buildGalleryImage(attr.tail, this.youtubePoster(attr.tail), description, 'youtube');
    }
    if (this.isImage(attr)) {
      return this.buildGalleryImage(attr.tail, attr.tail, description, 'image');
    }
    if (this.isVideo(attr)) {
      return this.buildGalleryImage(attr.tail, this.defaultVideoPoster(), description, 'video');
    }
    return this.buildGalleryImageFromArray(attr.attributes, description);
  }

  private static buildDescription(attribute: ApiAttribute, description: string): string {
      if (attribute.string === 'Title') {
        return attribute.tail;
      }
    return description;
  }

  private static buildGalleryImageFromArray(attributes: Array<ApiAttribute>,
    description: string): GalleryImage {
    for (const attribute of attributes) {
      description = this.buildDescription(attribute, description);
      if (this.isYouTube(attribute)) {
        return this.buildGalleryImage(attribute.tail, this.findPoster(attributes) || this.youtubePoster(attribute.tail), description, 'youtube');
      }
      if (this.isImage(attribute)) {
        return this.buildGalleryImage(attribute.tail, attribute.tail, description, 'image');
      }
      if (this.isVideo(attribute)) {
        return this.buildGalleryImage(attribute.tail, this.findPoster(attributes) || this.defaultVideoPoster(), description, 'video');
      }
      if (this.isMediaWrapper(attribute)) {
        return this.buildGalleryImageFromArray(attribute.attributes, description);
      }
    }
    return null;
  }

  private static buildGalleryImage(url: string, previewUrl: string, description: string, mediaType: 'image' | 'video' | 'youtube'): GalleryImage {
    const media: GalleryImage = {
      small: previewUrl,
      medium: previewUrl,
      big: url,
      description: description,
      url: url,
      mediaType,
    };

    if (mediaType === 'video') {
      media.videoData = JSON.stringify({
        source: [{ src: url, type: this.videoMimeType(url) }],
        attributes: { controls: true, preload: false, playsinline: true }
      });
    }

    return media;
  }

  private static videoMimeType(url: string): string {
    const tail = (url || '').toLowerCase();
    if (tail.endsWith('.webm')) {
      return 'video/webm';
    }
    if (tail.endsWith('.avi')) {
      return 'video/x-msvideo';
    }
    if (tail.endsWith('.mov')) {
      return 'video/quicktime';
    }
    if (tail.endsWith('.m4v')) {
      return 'video/x-m4v';
    }
    if (tail.endsWith('.mpg') || tail.endsWith('.mpeg')) {
      return 'video/mpeg';
    }
    return 'video/mp4';
  }

  private static findPoster(attributes: Array<ApiAttribute>): string | null {
    for (const attribute of attributes) {
      if (this.isImage(attribute)) {
        return attribute.tail;
      }
      if (attribute.attributes?.length) {
        const nestedPoster = this.findPoster(attribute.attributes);
        if (nestedPoster) {
          return nestedPoster;
        }
      }
    }
    return null;
  }

  private static youtubePoster(url: string): string {
    const id = this.youtubeId(url);
    if (!id) {
      return this.defaultVideoPoster();
    }
    return `https://img.youtube.com/vi/${id}/hqdefault.jpg`;
  }

  private static youtubeId(url: string): string | null {
    const normalized = (url || '').trim();
    const shortMatch = normalized.match(/youtu\.be\/([^?&]+)/i);
    if (shortMatch?.[1]) {
      return shortMatch[1];
    }
    const longMatch = normalized.match(/[?&]v=([^?&]+)/i);
    if (longMatch?.[1]) {
      return longMatch[1];
    }
    return null;
  }

  private static defaultVideoPoster(): string {
    // Transparent 1x1 fallback keeps layout stable if no linked image is present.
    return 'data:image/gif;base64,R0lGODlhAQABAIAAAAAAAP///ywAAAAAAQABAAACAUwAOw==';
  }
}
