import { ApiAttribute } from '../models';

export interface HasMultimedia {
  multimedia: Array<ApiAttribute>;
  save(): void;
  refreshMultimedia?(): void;
}
