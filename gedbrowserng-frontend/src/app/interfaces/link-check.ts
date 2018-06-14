import { ApiPerson } from '../models';

export interface LinkCheck {
  spouseLinked(person: ApiPerson): boolean;
  childLinked(person: ApiPerson): boolean;
}
