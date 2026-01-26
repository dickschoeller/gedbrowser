import { MultimediaDialogData, MultimediaFileData, MultimediaFormat, MultimediaSourceType } from './multimedia-dialog-data';

describe('MultimediaDialogData Model', () => {
  it('should create an instance', () => {
    const data = new MultimediaDialogData();
    expect(data).toBeTruthy();
  });

  it('should allow setting title', () => {
    const data = new MultimediaDialogData();
    data.title = 'Family Photos';
    expect(data.title).toBe('Family Photos');
  });

  it('should allow setting files', () => {
    const data = new MultimediaDialogData();
    const files: MultimediaFileData[] = [
      { fileUrl: 'photo1.jpg', format: MultimediaFormat.jpg }
    ];
    data.files = files;
    expect(data.files).toEqual(files);
  });

  it('should allow setting note', () => {
    const data = new MultimediaDialogData();
    data.note = 'A note about the media';
    expect(data.note).toBe('A note about the media');
  });
});

describe('MultimediaFileData Model', () => {
  it('should create an instance', () => {
    const fileData = new MultimediaFileData();
    expect(fileData).toBeTruthy();
  });

  it('should allow setting fileUrl', () => {
    const fileData = new MultimediaFileData();
    fileData.fileUrl = 'path/to/file.jpg';
    expect(fileData.fileUrl).toBe('path/to/file.jpg');
  });

  it('should allow setting format', () => {
    const fileData = new MultimediaFileData();
    fileData.format = MultimediaFormat.jpg;
    expect(fileData.format).toBe('jpg');
  });

  it('should allow setting sourceType', () => {
    const fileData = new MultimediaFileData();
    fileData.sourceType = MultimediaSourceType.photo;
    expect(fileData.sourceType).toBe('Photo');
  });
});

describe('MultimediaFormat Enum', () => {
  it('should have image format values', () => {
    expect(MultimediaFormat.bmp).toBe('bmp');
    expect(MultimediaFormat.gif).toBe('gif');
    expect(MultimediaFormat.jpg).toBe('jpg');
    expect(MultimediaFormat.png).toBe('png');
  });

  it('should have audio format values', () => {
    expect(MultimediaFormat.wav).toBe('wav');
    expect(MultimediaFormat.mp3).toBe('mp3');
  });

  it('should have video format values', () => {
    expect(MultimediaFormat.mp4).toBe('mp4');
    expect(MultimediaFormat.mov).toBe('mov');
  });

  it('should have document format values', () => {
    expect(MultimediaFormat.pdf).toBe('pdf');
    expect(MultimediaFormat.docx).toBe('docx');
  });
});

describe('MultimediaSourceType Enum', () => {
  it('should have source type values', () => {
    expect(MultimediaSourceType.audio).toBe('Audio');
    expect(MultimediaSourceType.photo).toBe('Photo');
    expect(MultimediaSourceType.video).toBe('Video');
    expect(MultimediaSourceType.book).toBe('Book');
    expect(MultimediaSourceType.newspaper).toBe('Newspaper');
  });
});
