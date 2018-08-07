import { HasAttributeList } from '../interfaces';
import { ApiAttribute } from '../models';

export class Refresher {
  public static refresh(parent: HasAttributeList, type: string, string: string) {
    const attribute: ApiAttribute = {
      type: type,
      string: string,
      tail: '',
      attributes: new Array<ApiAttribute>()
    };
    parent.attributes.push(attribute);
    parent.save();
  }
}
