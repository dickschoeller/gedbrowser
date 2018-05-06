import {SelectItem} from 'primeng/api';

import {AttributeDialogData} from '../components/attribute-dialog';

export interface HasAttributeList {
  options(): Array<SelectItem>;

  defaultData(): AttributeDialogData;

  save(): void;
}
