import { ApiAttribute } from '../models';

export class ImageUtil {
  constructor(private attribute: ApiAttribute) {}

  isImage(): boolean {
    const types = [ 'bmp', 'gif', 'ico', 'jpg', 'jpeg', 'png', 'tiff', 'tif', 'svg' ];
    for (const t of types) {
      if (this.attribute.tail.toLowerCase().endsWith(t)) {
        return true;
      }
    }
    return false;
  }
}
