import {ApiAttribute} from './api-attribute.model';
import {ApiObject} from './api-object.model';

export class ApiSource extends ApiObject {
  title = '';
  images: Array<ApiAttribute> = [];
}
