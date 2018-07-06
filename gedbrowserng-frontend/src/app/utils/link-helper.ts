import { LinkDialogInterface } from '../interfaces';
import { ApiAttribute, LinkDialogData, LinkItem, ApiObject, } from '../models';

export class LinkHelper {
  constructor(private labelString,
    private comparator,
    private linkString) {}

  onLinkDialogOpen(service, dialog: LinkDialogInterface) {
    service.getAll(dialog.dataset).subscribe(
      (value: ApiObject[]) => this.fillLinkData(dialog, value)
    );
  }

  fillLinkData(dialog: LinkDialogInterface, value) {
    dialog.objects = value;
    dialog.objects.sort(this.comparator);
    dialog._data = new LinkDialogData();
    let index = 1;
    for (const obj of dialog.objects) {
      this.pushObject(index++, dialog, obj);
    }
  }

  link(data: LinkDialogData, attributes: Array<ApiAttribute>, save) {
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

  onUnlinkDialogOpen(service, dialog: LinkDialogInterface, attributes: Array<ApiAttribute>) {
    service.getAll(dialog.dataset).subscribe(
      (value: ApiObject[]) =>
        this.fillUnlinkData(dialog, value, attributes)
    );
  }

  fillUnlinkData(dialog: LinkDialogInterface, value, attributes: Array<ApiAttribute>) {
    dialog.objects = value;
    dialog.objects.sort(this.comparator);
    dialog._data = new LinkDialogData();
    let index = 1;
    for (const attribute of attributes) {
      if (attribute.type === this.linkString) {
        this.pushObject(index++, dialog, attribute);
      }
    }
  }

  pushObject(index: number, data: LinkDialogInterface, obj: ApiObject) {
    data._data.items.push({
      index: index,
      id: obj.string,
      label: index + ' ' + this.find(obj.string, data.objects) + ' [' + obj.string + ']'
    });
  }

  find(submitterId: string, submitters: Array<ApiObject>): string {
    for (const submitter of submitters) {
      if (submitter.string === submitterId) {
        return this.labelString(submitter);
      }
    }
    return undefined;
  }

  unlink(data: LinkDialogData, attributes: Array<ApiAttribute>, save) {
        for (const item of data.selected) {
      this.spliceOutOne(item, attributes);
    }
    save();
  }

  spliceOutOne(item: LinkItem, attributes: Array<ApiAttribute>) {
    let index = 0;
    for (const attribute of attributes) {
      if (attribute.string === item.id) {
        attributes.splice(index, 1);
        break;
      }
      index++;
    }
  }
}
