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
    const selectedPerson = new ApiPerson();
    selectedPerson.string = personId;
    const selected: LinkPersonItem = {
      id: personId,
      label: personId,
      person: selectedPerson
    };
    const data = new LinkPersonDialogData();
    data.selected = [selected];
    data.selectOne = selected;
    return data;
  }
}

export class LinkPersonItem {
  id: string;
  label: string;
  person: ApiPerson;
}
