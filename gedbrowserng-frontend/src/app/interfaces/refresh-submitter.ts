import { ApiSubmitter } from '../models';

export interface RefreshSubmitter {
  refreshSubmitter(submitter: ApiSubmitter): void;
}
