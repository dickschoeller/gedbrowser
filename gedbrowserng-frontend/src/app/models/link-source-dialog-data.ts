export class LinkSourceDialogData {
  items: Array<LinkSourceItem>;
  selected: Array<LinkSourceItem>;
}

export class LinkSourceItem {
  index: number;
  id: string;
  title: string;
}
