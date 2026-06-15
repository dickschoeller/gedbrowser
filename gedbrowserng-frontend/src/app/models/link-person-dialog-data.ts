import { ApiPerson } from './';
export class LinkPersonDialogData {
  constructor() {
    this.items = new Array<LinkPersonItem>();
    this.selected = new Array<LinkPersonItem>();
  }
  multi: boolean;
  items: Array<LinkPersonItem>;
  selected: Array<LinkPersonItem>;
  selectOne: LinkPersonItem;
  dataset: string;
  titleString: string;

  static fromPersonId(personId: string): LinkPersonDialogData {
    return LinkPersonDialogData.fromPersonIds([personId]);
  }

  static fromPersonIds(personIds: string[]): LinkPersonDialogData {
    const data = new LinkPersonDialogData();
    data.selected = personIds.map(personId => {
      const person = new ApiPerson();
      person.string = personId;
      return { id: personId, label: personId, person };
    });
    data.selectOne = data.selected[0];
    return data;
  }
}

export class LinkPersonItem {
  id: string;
  label: string;
  person: ApiPerson;
}
