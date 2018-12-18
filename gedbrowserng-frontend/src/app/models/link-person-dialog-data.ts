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
}

export class LinkPersonItem {
  id: string;
  label: string;
  person: ApiPerson;
}
