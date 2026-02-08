import { LinkDialogInterface } from '../interfaces';
import { ApiAttribute, LinkDialogData, LinkItem, ApiObject, } from '../models';
import { LinkDialogActions } from './link-dialog-actions';

export class LinkHelper extends LinkDialogActions {
  constructor(public readonly labelString, private readonly comparator, private readonly linkString) {
    super(labelString);
  }

  onOpen(service, dialog: LinkDialogInterface, attributes: Array<ApiAttribute>) {
    service.getAll(dialog.data.dataset).subscribe(
      (value: ApiObject[]) => this.fillLinkData(dialog, value)
    );
  }

  fillLinkData(dialog: LinkDialogInterface, value: ApiObject[]) {
    dialog.objects = value;
    dialog.objects = dialog.objects.toSorted(this.comparator);
    dialog.data.items = new Array<LinkItem>();
    let index = 1;
    for (const obj of dialog.objects) {
      this.pushObject(index++, dialog, obj);
    }
  }

  onOK(data: LinkDialogData, attributes: Array<ApiAttribute>, save) {
    for (const item of data.selected) {
      const attribute: ApiAttribute = {
        type: this.linkString,
        string: item.id,
        tail: '',
        attributes: new Array<ApiAttribute>()
      };
      attributes.push(attribute);
    }
    save();
  }
}
