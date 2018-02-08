import {ApiAttribute} from '../models';
import {NgxGalleryImage} from 'ngx-gallery';

export class ImageUtil {
  constructor(private attribute: ApiAttribute) {}

  isImageWrapper(): boolean {
    for (const attribute of this.attribute.attributes) {
      const imageUtil = new ImageUtil(attribute);
      if (imageUtil.isImage()) {
        return true;
      }
      if (imageUtil.isImageWrapper()) {
        return true;
      }
    }
    return false;
  }

  isImage(): boolean {
    const types = [ 'bmp', 'gif', 'ico', 'jpg', 'jpeg', 'png', 'tiff', 'tif', 'svg' ];
    for (const t of types) {
      if (this.attribute.tail.toLowerCase().endsWith(t)) {
        return true;
      }
    }
    return false;
  }

  galleryImage(): NgxGalleryImage {
    if (this.isImage()) {
      return { small: this.attribute.tail, medium: this.attribute.tail,
        big: this.attribute.tail, description: 'foo', url: this.attribute.tail };
    }
    for (const attribute of this.attribute.attributes) {
      const imageUtil = new ImageUtil(attribute);
      if (imageUtil.isImage()) {
        return imageUtil.galleryImage();
      }
      if (imageUtil.isImageWrapper()) {
        for (const subAttribute of attribute.attributes) {
          return new ImageUtil(subAttribute).galleryImage();
        }
      }
    }
    return null;
  }
}
