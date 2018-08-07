import { ApiNote } from '../models';

export interface RefreshNote {
  refreshNote(note: ApiNote): void;
}
