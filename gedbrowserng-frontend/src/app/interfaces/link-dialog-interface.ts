import { LinkDialogData } from '../models';

export interface LinkDialogInterface {
  titleString: string;
  dataset: string;
  objects: Array<any>;
  _data: LinkDialogData;
}
