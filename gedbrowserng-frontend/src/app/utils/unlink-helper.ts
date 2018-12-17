import { LinkDialogInterface } from '../interfaces';
import { ApiAttribute, ApiObject, LinkItem, LinkDialogData } from '../models';
import { LinkDialogActions } from './link-dialog-actions';

export class UnlinkHelper extends LinkDialogActions {
  constructor(public labelString, private comparator, private linkString) {
    super(labelString);
  }

  onOpen(service, dialog: LinkDialogInterface, attributes: Array<ApiAttribute>) {
    service.getAll(dialog.data.dataset).subscribe(
      (value: ApiObject[]) => this.fillUnlinkData(dialog, value, attributes)
    );
  }

  fillUnlinkData(dialog: LinkDialogInterface, value, attributes: Array<ApiAttribute>) {
    dialog.objects = value;
    dialog.objects.sort(this.comparator);
    dialog.data.items = new Array<LinkItem>();
    let index = 1;
    for (const attribute of attributes) {
      if (attribute.type === this.linkString) {
        this.pushObject(index++, dialog, attribute);
      }
    }
  }

  onOK(data: LinkDialogData, attributes: Array<ApiAttribute>, save) {
    for (const item of data.selected) {
      this.spliceOutOne(item, attributes);
    }
    save();
  }
}
