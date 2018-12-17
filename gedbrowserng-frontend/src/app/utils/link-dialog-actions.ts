import { LinkDialogInterface } from '../interfaces';
import { ApiObject, ApiAttribute, LinkItem, LinkDialogData } from '../models';

export abstract class LinkDialogActions {
  constructor(public labelString) { }

  pushObject(index: number, dialog: LinkDialogInterface, obj: ApiObject) {
    dialog.data.items.push({
      index: index,
      id: obj.string,
      label: this.find(obj.string, dialog.objects) + ' [' + obj.string + ']'
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

  abstract onOpen(service, dialog: LinkDialogInterface, attributes: Array<ApiAttribute>);

  abstract onOK(data: LinkDialogData, attributes: Array<ApiAttribute>, save);
}
