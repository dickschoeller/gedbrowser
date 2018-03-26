import {ApiAttribute} from './api-attribute.model';
import {ApiObject} from './api-object.model';

export class ApiFamily extends ApiObject {
  spouses: Array<ApiAttribute> = [];
  children: Array<ApiAttribute> = [];
  images: Array<ApiAttribute> = [];
}
