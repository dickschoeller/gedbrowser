import { SelectItem } from 'primeng/api';

import { ApiAttribute, AttributeDialogData } from '../models';

export interface HasAttributeList {
  attributes: Array<ApiAttribute>;

  options(): Array<SelectItem>;

  defaultData(): AttributeDialogData;

  save(): void;
}
