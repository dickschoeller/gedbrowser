import {PersonService} from '../../services';

export class PersonGetter {
  constructor(private personService: PersonService) {}

  get(dataset: string, id: string, callback: any): void {
    this.personService.getOne(dataset, id)
      .subscribe(callback);
  }
}
