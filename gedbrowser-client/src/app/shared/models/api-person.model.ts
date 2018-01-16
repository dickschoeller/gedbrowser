import { ApiObject } from './api-object.model';
import { ApiLifespan } from './api-lifespan.model';

export class ApiPerson extends ApiObject {
  indexName = '';
  surname = '';
  lifespan: ApiLifespan;
}
