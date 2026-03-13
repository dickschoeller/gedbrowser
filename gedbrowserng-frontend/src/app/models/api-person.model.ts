import {ApiAttribute} from './api-attribute.model';
import {ApiLifespan} from './api-lifespan.model';
import {ApiObject} from './api-object.model';
import {PlaceInfo} from './place-info.model';

export class ApiPerson extends ApiObject {
  indexName = '';
  surname = '';
  lifespan: ApiLifespan;
  famss: Array<ApiAttribute> = [];
  famcs: Array<ApiAttribute> = [];
  refns: Array<ApiAttribute> = [];
  changes: Array<ApiAttribute> = [];
  images: Array<ApiAttribute> = [];
  places: Array<PlaceInfo> = [];
}
