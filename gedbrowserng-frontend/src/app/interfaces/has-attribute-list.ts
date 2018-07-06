import { SelectItem } from 'primeng/api';

import { AttributeDialogData } from '../components/attribute-dialog';
import { ApiAttribute } from '../models';

export interface HasAttributeList {
  attributes: Array<ApiAttribute>;

  options(): Array<SelectItem>;

  defaultData(): AttributeDialogData;

  save(): void;
}
