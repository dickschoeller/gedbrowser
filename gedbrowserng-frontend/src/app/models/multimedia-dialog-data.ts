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
  bmp = 'bmp',
  gif = 'gif',
  jpg = 'jpg',
  pcx = 'pcx',
  tif = 'tif',
  png = 'png',
  // Audio formats
  wav = 'wav',
  mp3 = 'mp3',
  flac = 'flac',
  ogg = 'ogg',
  // Video formats
  avi = 'avi',
  m4v = 'm4v',
  mpg = 'mpg',
  mp4 = 'mp4',
  mov = 'mov',
  // Document formats
  doc = 'doc',
  docx = 'docx',
  pdf = 'pdf',
  // Website link
  html = 'html',
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
