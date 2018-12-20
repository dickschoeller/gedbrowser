import { RefreshPerson } from '../../interfaces';
import {ApiPerson} from '../../models';
import {PersonService } from '../../services';
import {UrlBuilder, LifespanUtil} from '../../utils';

export abstract class PersonGetter implements RefreshPerson {
  dataset: string;
  person: ApiPerson;
  famMemberType: string;

  constructor(private personService: PersonService) {}

  abstract refreshPerson(): void;
  abstract familyString(): string;

  init(dataset: string, attrString: string): void {
    this.dataset = dataset;
    this.get(dataset, attrString, (person: ApiPerson) => {
      this.person = person;
    });
  }

  get(dataset: string, id: string, callback: any): void {
    this.personService.getOne(dataset, id)
      .subscribe(callback);
  }

  unlink(): void {
    const ub: UrlBuilder = new UrlBuilder(this.dataset, 'families', this.famMemberType);
    this.personService.deleteLink(ub, this.familyString(), this.person)
      .subscribe((data: ApiPerson) => { this.refreshPerson(); });
  }

  lifespanYearString(): string {
    return new LifespanUtil(this.person.lifespan).lifespanYearString();
  }
}
