import { RefreshPerson } from '../interfaces';
import { ApiPerson, NewPersonDialogData, LinkPersonDialogData } from '../models';
import { NewPersonHelper, UrlBuilder } from '../utils';
import { NewPersonLinkService } from '../services';

export abstract class PersonCreator implements RefreshPerson {
  constructor(public newPersonLinkService: NewPersonLinkService) {}

  createPerson(data: NewPersonDialogData): void {
    if (data != null) {
      const newPerson: ApiPerson = NewPersonHelper.buildPerson(data);
      this.newPersonLinkService.post(this.personUB(), this.personAnchor(), newPerson)
        .subscribe((d: ApiPerson) => this.refreshPerson());
    }
  }

  linkParent(data: LinkPersonDialogData) {
    this.newPersonLinkService.put(this.personUB(), this.personAnchor(), data.selectOne.person)
      .subscribe((person: ApiPerson) => { this.refreshPerson(); });
  }

  abstract personUB(): UrlBuilder;

  abstract personAnchor(): string;

  abstract refreshPerson(): void;
}
