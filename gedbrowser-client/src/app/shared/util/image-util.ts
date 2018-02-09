import {ApiAttribute} from '../models';
import {NgxGalleryImage} from 'ngx-gallery';

export class ImageUtil {
  constructor() {}

  imageAttributes(attributes: Array<ApiAttribute>): Array<ApiAttribute> {
    const images: Array<ApiAttribute> = new Array<ApiAttribute>();
    for (const attribute of attributes) {
      if (this.isImageWrapper(attribute)) {
        images.push(attribute);
      }
    }
    return images;
  }

  galleryImages(attributes: Array<ApiAttribute>): Array<NgxGalleryImage> {
    const gallery: Array<NgxGalleryImage> = new Array<NgxGalleryImage>();
    for (const attribute of this.imageAttributes(attributes)) {
      gallery.push(this.galleryImage(attribute));
    }
    return gallery;
  }

  isImageWrapper(attr: ApiAttribute): boolean {
    for (const attribute of attr.attributes) {
      if (this.isImage(attribute)) {
        return true;
      }
      if (this.isImageWrapper(attribute)) {
        return true;
      }
    }
    return false;
  }

  isImage(attr: ApiAttribute): boolean {
    const types = [ 'bmp', 'gif', 'ico', 'jpg', 'jpeg', 'png', 'tiff', 'tif', 'svg' ];
    for (const t of types) {
      if (attr.tail.toLowerCase().endsWith(t)) {
        return true;
      }
    }
    return false;
  }

  galleryImage(attr: ApiAttribute): NgxGalleryImage {
    if (this.isImage(attr)) {
      return { small: attr.tail, medium: attr.tail,
        big: attr.tail, description: 'foo', url: attr.tail };
    }
    for (const attribute of attr.attributes) {
      if (this.isImage(attribute)) {
        return this.galleryImage(attribute);
      }
      if (this.isImageWrapper(attribute)) {
        for (const subAttribute of attribute.attributes) {
          if (this.isImage(subAttribute)) {
            return this.galleryImage(subAttribute);
          }
        }
      }
    }
    return null;
  }
}
