export interface AttributeDialogData {
  insert: boolean;
  index: number;
  type: string;
  text: string;
  date: string;
  place: string;
  modernPlace?: string;
  note: string;

  originalType: string;
  originalText: string;
  originalDate: string;
  originalPlace: string;
  originalModernPlace?: string;
  originalNote: string;

  deleted?: boolean;
}
