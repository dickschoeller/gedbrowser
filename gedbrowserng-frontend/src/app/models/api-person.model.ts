import {ApiAttribute} from './api-attribute.model';
import {ApiLifespan} from './api-lifespan.model';
import {ApiObject} from './api-object.model';

export class ApiPerson extends ApiObject {
  indexName = '';
  surname = '';
  lifespan: ApiLifespan;
  fams: Array<ApiAttribute> = [];
  famc: Array<ApiAttribute> = [];
  refn: Array<ApiAttribute> = [];
  changed: Array<ApiAttribute> = [];
  images: Array<ApiAttribute> = [];
}
