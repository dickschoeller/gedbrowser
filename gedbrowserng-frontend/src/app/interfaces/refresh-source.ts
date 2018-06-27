import { ApiSource } from '../models';

export interface RefreshSource {
  refreshSource(source: ApiSource): void;
}
