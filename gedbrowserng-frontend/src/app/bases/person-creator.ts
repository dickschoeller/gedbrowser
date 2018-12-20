import { RefreshPerson } from '../interfaces';
import { ApiPerson, NewPersonDialogData, LinkPersonDialogData } from '../models';
import { NewPersonHelper, UrlBuilder } from '../utils';
import { PersonService } from '../services';

export abstract class PersonCreator implements RefreshPerson {
  constructor(public personService: PersonService) {}

  createPerson(data: NewPersonDialogData): void {
    if (data != null) {
      const newPerson: ApiPerson = NewPersonHelper.buildPerson(data);
      this.personService.postLink(this.personUB(), this.personAnchor(), newPerson)
        .subscribe((d: ApiPerson) => this.refreshPerson());
    }
  }

  linkPerson(data: LinkPersonDialogData) {
    this.personService.putLink(this.personUB(), this.personAnchor(), data.selectOne.person)
      .subscribe((person: ApiPerson) => { this.refreshPerson(); });
  }

  abstract personUB(): UrlBuilder;

  abstract personAnchor(): string;

  abstract refreshPerson(): void;
}
