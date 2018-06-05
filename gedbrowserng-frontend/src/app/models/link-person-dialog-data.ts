import { ApiPerson } from './';
export class LinkPersonDialogData {
  constructor() {
    this.items = new Array<LinkPersonItem>();
    this.selected = new Array<LinkPersonItem>();
  }
  items: Array<LinkPersonItem>;
  selected: Array<LinkPersonItem>;
  selectOne: LinkPersonItem;
}

export class LinkPersonItem {
  id: string;
  label: string;
  person: ApiPerson;
}
