export class LinkSourceDialogData {
  constructor() {
    this.items = new Array<LinkSourceItem>();
    this.selected = new Array<LinkSourceItem>();
  }
  items: Array<LinkSourceItem>;
  selected: Array<LinkSourceItem>;
}

export class LinkSourceItem {
  index: number;
  id: string;
  title: string;
}
