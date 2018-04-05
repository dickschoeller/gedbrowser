import {ApiAttribute} from './api-attribute.model';

export class ApiObject {
  type = '';
  string: string;
  attributes: Array<ApiAttribute> = [];
}
