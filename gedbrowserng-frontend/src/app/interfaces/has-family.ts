import { ApiFamily } from '../models';
import { RefreshPerson } from './refresh-person';

export interface HasFamily extends RefreshPerson {
  family: ApiFamily;
  preferredSurname(): string;
  familyString(): string;
}
