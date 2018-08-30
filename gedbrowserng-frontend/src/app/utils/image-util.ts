import { NgxGalleryImage, NgxGalleryOptions } from 'ngx-gallery';

import { ApiAttribute } from '../models';

export class ImageUtil {
  constructor() {}

  public static imageAttributes(attributes: Array<ApiAttribute>): Array<ApiAttribute> {
    const images: Array<ApiAttribute> = new Array<ApiAttribute>();
    for (const attribute of attributes) {
      if (this.isImageWrapper(attribute)) {
        images.push(attribute);
      }
    }
    return images;
  }

  public static galleryImages(attributes: Array<ApiAttribute>): Array<NgxGalleryImage> {
    const gallery: Array<NgxGalleryImage> = new Array<NgxGalleryImage>();
    for (const attribute of this.imageAttributes(attributes)) {
      gallery.push(this.galleryImage(attribute));
    }
    return gallery;
  }

  public static isImageWrapper(attr: ApiAttribute): boolean {
    for (const attribute of attr.attributes) {
      if (this.isImage(attribute) || this.isImageWrapper(attribute)) {
        return true;
      }
    }
    return false;
  }

  public static isImage(attribute: ApiAttribute): boolean {
    const types = [ 'bmp', 'gif', 'ico', 'jpg', 'jpeg', 'png', 'tiff', 'tif', 'svg' ];
    for (const t of types) {
      if (attribute.tail.toLowerCase().endsWith(t)) {
        return true;
      }
    }
    return false;
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

  public static galleryImage(attr: ApiAttribute): NgxGalleryImage {
    const description = 'Image';
    if (this.isImage(attr)) {
      return this.buildGalleryImage(attr.tail, description);
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
    description: string): NgxGalleryImage {
    for (const attribute of attributes) {
      description = this.buildDescription(attribute, description);
      if (this.isImage(attribute)) {
        return this.buildGalleryImage(attribute.tail, description);
      }
      if (this.isImageWrapper(attribute)) {
        return this.buildGalleryImageFromArray(attribute.attributes, description);
      }
    }
    return null;
  }

  private static buildGalleryImage(url: string, description: string): NgxGalleryImage {
    return {
      small: url, medium: url, big: url, description: description, url: url
    };
  }
}
