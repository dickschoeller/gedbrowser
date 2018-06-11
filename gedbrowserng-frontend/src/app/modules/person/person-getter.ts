import { ApiPerson } from '../../models';
import {PersonService} from '../../services';

export class PersonGetter {
  person: ApiPerson;

  constructor(private personService: PersonService) {}

  init(dataset: string, attrString: string): void {
    this.get(dataset, attrString, (person: ApiPerson) => {
      this.person = person;
    });
  }

  get(dataset: string, id: string, callback: any): void {
    this.personService.getOne(dataset, id)
      .subscribe(callback);
  }
}
