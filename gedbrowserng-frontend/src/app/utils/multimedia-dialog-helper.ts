import { MultimediaDialogData, ApiAttribute, MultimediaFileData, MultimediaFormat, MultimediaSourceType } from '../models';
import { ArrayUtil } from './array-util';
import { ImageUtil } from './image-util';
import { StringUtil } from './string-util';
export class MultimediaDialogHelper {
  public static buildMultimediaAttribute(data: MultimediaDialogData): ApiAttribute {
    const attribute: ApiAttribute = {
      type: 'multimedia', string: 'Multimedia', tail: '',
      attributes: [
        { type: 'attribute', string: 'Title', tail: data.title, attributes: [] },
      ]
    };
    for (const mFile of data.files) {
      attribute.attributes.push(this.buildFileAttribute(mFile));
    }
    if (!StringUtil.isEmpty(data.note)) {
      attribute.attributes.push({ type: 'attribute', string: 'Note', tail: data.note, attributes: [] });
    }
    return attribute;
  }

  private static buildFileAttribute(mFile: MultimediaFileData): ApiAttribute {
    return {
      type: 'attribute', string: 'File', tail: mFile.fileUrl,
      attributes: [
        {
          type: 'attribute', string: 'Format', tail: mFile.format.toLowerCase(), attributes: [
            { type: 'attribute', string: 'Media', tail: mFile.sourceType.toLowerCase(), attributes: [] }
          ]
        },
      ]
    };
  }

  public static buildMultimediaDialogData(multimedias: Array<ApiAttribute>, dialogIndex?: number | 0): MultimediaDialogData {
    const files: Array<MultimediaFileData> = new Array<MultimediaFileData>();
    if (ArrayUtil.isEmpty(multimedias)) {
      return { title: 'Title', files: [], note: '' };
    }
    const multimedia: ApiAttribute = multimedias[dialogIndex];
    let title = 'Title ' + dialogIndex;
    let note = '';
    for (const attribute of multimedia.attributes) {
      title = this.buildTitle(attribute, title);
      this.pushFile(files, attribute);
      note = this.buildNote(attribute, note);
    }
    return { title: title, files: files, note: note };
  }

  private static buildTitle(attribute: ApiAttribute, title: string): string {
    if (attribute.string === 'Title') {
      return attribute.tail;
    }
    return title;
  }

  private static pushFile(files: Array<MultimediaFileData>, attribute: ApiAttribute) {
    if (attribute.string !== 'File') {
      return;
    }
    files.push(this.buildFile(attribute));
  }

  private static buildFile(attribute: ApiAttribute) {
    const formatString = this.imageFormat(attribute);
    if (StringUtil.isEmpty(formatString)) {
      return { fileUrl: attribute.tail };
    }

    const sourceTypeString = this.sourceType(attribute);
    if (StringUtil.isEmpty(sourceTypeString)) {
      return {
        fileUrl: attribute.tail,
        format: MultimediaFormat[formatString],
      };
    }
    return {
      fileUrl: attribute.tail,
      format: MultimediaFormat[formatString],
      sourceType: MultimediaSourceType[sourceTypeString]
    };
  }

  private static buildNote(attribute: ApiAttribute, note: string): string {
    if (attribute.type === 'attribute' && attribute.string === 'Note' && StringUtil.isEmpty(note)) {
      note = attribute.tail;
    }
    return note;
  }

  private static imageFormat(file: ApiAttribute): string {
    for (const attribute of file.attributes) {
      if (attribute.string === 'Format') {
        return attribute.tail;
      }
    }
    return this.fileFormat(file);
  }

  public static fileFormat(file: ApiAttribute): string {
    for (const t of Object.keys(MultimediaFormat)) {
      if (file.tail.toLowerCase().endsWith(t)) {
        return t;
      }
    }
    return '';
  }

  private static sourceType(file: ApiAttribute): string {
    for (const attribute of file.attributes) {
      if (attribute.string === 'Format' && !ArrayUtil.isEmpty(attribute.attributes)) {
        return attribute.attributes[0].tail;
      }
    }
    return '';
  }
}
