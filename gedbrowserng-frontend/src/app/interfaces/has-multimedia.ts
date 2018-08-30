import { SelectItem } from 'primeng/api';

import { ApiAttribute, AttributeDialogData } from '../models';

export interface HasMultimedia {
  multimedia: Array<ApiAttribute>;
  save(): void;
}
