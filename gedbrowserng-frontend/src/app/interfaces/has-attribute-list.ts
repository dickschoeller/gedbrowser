import { ApiAttribute, AttributeDialogData, SelectItem } from '../models';

export interface HasAttributeList {
  attributes: Array<ApiAttribute>;

  options(): Array<SelectItem>;

  defaultData(): AttributeDialogData;

  save(): void;
}
