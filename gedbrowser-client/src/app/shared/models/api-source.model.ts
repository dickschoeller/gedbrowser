import {ApiObject} from './api-object.model';
import {ApiAttribute} from './api-attribute.model';

export class ApiSource extends ApiObject {
  title = '';
  images: Array<ApiAttribute> = [];
}
