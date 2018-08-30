export class MultimediaDialogData {
  title: string;
  files: Array<MultimediaFileData>;
  note?: string;
}

export class MultimediaFileData {
  fileUrl: string;
  format?: MultimediaFormat;
  sourceType?: MultimediaSourceType;
}

export enum MultimediaFormat {
  // Image formats
  bmp = 'BMP',
  gif = 'GIF',
  jpg = 'JPEG',
  pcx = 'PCX',
  tif = 'TIFF',
  png = 'PNG',
  // Audio formats
  wav = 'WAV',
  mp3 = 'MP3',
  flac = 'FLAC',
  ogg = 'OGG',
  // Video formats
  avi = 'AVI',
  mpg = 'MPG',
  mp4 = 'MP4',
  mov = 'MOV',
  // Document formats
  doc = 'DOC',
  docx = 'DOCX',
  pdf = 'PDF',
  // Website link
  html = 'HTML',
}

export enum MultimediaSourceType {
  audio = 'Audio',
  book = 'Book',
  card = 'Card',
  electronic = 'Electronic',
  fiche = 'Fiche',
  film = 'Film',
  magazine = 'Magazine',
  manuscript = 'Manuscript',
  map = 'Map',
  newspaper = 'Newspaper',
  photo = 'Photo',
  tombstone = 'Tombstone',
  video = 'Video',
}
