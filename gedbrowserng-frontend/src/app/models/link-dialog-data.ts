export class LinkDialogData {
  items: Array<LinkItem>;
  selected: Array<LinkItem>;
  constructor() {
    this.items = new Array<LinkItem>();
    this.selected = new Array<LinkItem>();
  }
}

export class LinkItem {
  index: number;
  id: string;
  label: string;
}
