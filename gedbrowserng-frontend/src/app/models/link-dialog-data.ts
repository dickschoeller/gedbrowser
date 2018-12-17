export class LinkDialogData {
  name: string;
  items: Array<LinkItem>;
  selected: Array<LinkItem>;
  dataset: string;
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
