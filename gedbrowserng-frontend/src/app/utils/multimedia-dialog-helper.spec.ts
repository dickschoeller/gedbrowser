import { describe, it, expect } from 'vitest';
import { MultimediaDialogHelper } from './multimedia-dialog-helper';
import { MultimediaDialogData } from '../models';

describe('MultimediaDialogHelper', () => {
  describe('buildMultimediaAttribute()', () => {
    it('creates multimedia attribute with title and files', () => {
      const data: MultimediaDialogData = {
        title: 'Photo Album',
        files: [
          { fileUrl: 'photo1.jpg', format: 'jpg' as any, sourceType: 'electronic' as any }
        ],
        note: 'Family photos'
      };

      const attribute = MultimediaDialogHelper.buildMultimediaAttribute(data);

      expect(attribute.type).toBe('multimedia');
      expect(attribute.string).toBe('Multimedia');
      expect(attribute.attributes.length).toBeGreaterThan(1);
    });

    it('includes title in first attribute', () => {
      const data: MultimediaDialogData = {
        title: 'My Title',
        files: [],
        note: ''
      };

      const attribute = MultimediaDialogHelper.buildMultimediaAttribute(data);
      const titleAttr = attribute.attributes.find((a: any) => a.string === 'Title');

      expect(titleAttr).toBeDefined();
      expect(titleAttr.tail).toBe('My Title');
    });

    it('includes all files', () => {
      const data: MultimediaDialogData = {
        title: 'Photos',
        files: [
          { fileUrl: 'photo1.jpg', format: 'jpg' as any, sourceType: 'electronic' as any },
          { fileUrl: 'photo2.png', format: 'png' as any, sourceType: 'electronic' as any }
        ],
        note: ''
      };

      const attribute = MultimediaDialogHelper.buildMultimediaAttribute(data);
      const fileAttrs = attribute.attributes.filter((a: any) => a.string === 'File');

      expect(fileAttrs.length).toBe(2);
    });

    it('includes note when not empty', () => {
      const data: MultimediaDialogData = {
        title: 'Photos',
        files: [],
        note: 'Important photos'
      };

      const attribute = MultimediaDialogHelper.buildMultimediaAttribute(data);
      const noteAttr = attribute.attributes.find((a: any) => a.string === 'Note');

      expect(noteAttr).toBeDefined();
      expect(noteAttr.tail).toBe('Important photos');
    });

    it('omits note when empty', () => {
      const data: MultimediaDialogData = {
        title: 'Photos',
        files: [],
        note: ''
      };

      const attribute = MultimediaDialogHelper.buildMultimediaAttribute(data);
      const noteAttrs = attribute.attributes.filter((a: any) => a.string === 'Note');

      expect(noteAttrs.length).toBe(0);
    });

    it('handles file with source type', () => {
      const data: MultimediaDialogData = {
        title: 'Photos',
        files: [
          {
            fileUrl: 'photo.jpg',
            format: 'jpg' as any,
            sourceType: 'electronic' as any
          }
        ],
        note: ''
      };

      const attribute = MultimediaDialogHelper.buildMultimediaAttribute(data);
      const fileAttr = attribute.attributes.find((a: any) => a.string === 'File');

      expect(fileAttr.attributes).toBeDefined();
      expect(fileAttr.attributes.length).toBeGreaterThan(0);
    });
  });

  describe('buildMultimediaDialogData()', () => {
    it('returns default data for empty multimedias', () => {
      const data = MultimediaDialogHelper.buildMultimediaDialogData([]);

      expect(data.title).toBe('Title');
      expect(data.files).toEqual([]);
      expect(data.note).toBe('');
    });

    it('extracts title from multimedia attribute', () => {
      const multimedias: ApiAttribute[] = [
        {
          type: 'multimedia',
          string: 'Multimedia',
          tail: '',
          attributes: [
            { type: 'attribute', string: 'Title', tail: 'Family Photos', attributes: [] }
          ]
        }
      ];

      const data = MultimediaDialogHelper.buildMultimediaDialogData(multimedias, 0);

      expect(data.title).toBe('Family Photos');
    });

    it('extracts files from multimedia', () => {
      const multimedias: ApiAttribute[] = [
        {
          type: 'multimedia',
          string: 'Multimedia',
          tail: '',
          attributes: [
            {
              type: 'attribute',
              string: 'File',
              tail: 'photo.jpg',
              attributes: [
                {
                  type: 'attribute',
                  string: 'Format',
                  tail: 'jpg',
                  attributes: []
                }
              ]
            }
          ]
        }
      ];

      const data = MultimediaDialogHelper.buildMultimediaDialogData(multimedias, 0);

      expect(data.files.length).toBe(1);
      expect(data.files[0].fileUrl).toBe('photo.jpg');
    });

    it('extracts note from multimedia', () => {
      const multimedias: ApiAttribute[] = [
        {
          type: 'multimedia',
          string: 'Multimedia',
          tail: '',
          attributes: [
            { type: 'attribute', string: 'Note', tail: 'Some note', attributes: [] }
          ]
        }
      ];

      const data = MultimediaDialogHelper.buildMultimediaDialogData(multimedias, 0);

      expect(data.note).toBe('Some note');
    });

    it('extracts format from file attribute', () => {
      const multimedias: ApiAttribute[] = [
        {
          type: 'multimedia',
          string: 'Multimedia',
          tail: '',
          attributes: [
            {
              type: 'attribute',
              string: 'File',
              tail: 'photo.jpg',
              attributes: [
                {
                  type: 'attribute',
                  string: 'Format',
                  tail: 'jpg',
                  attributes: []
                }
              ]
            }
          ]
        }
      ];

      const data = MultimediaDialogHelper.buildMultimediaDialogData(multimedias, 0);

      expect(data.files[0].format).toBe('jpg');
    });

    it('extracts source type from file attribute', () => {
      const multimedias: ApiAttribute[] = [
        {
          type: 'multimedia',
          string: 'Multimedia',
          tail: '',
          attributes: [
            {
              type: 'attribute',
              string: 'File',
              tail: 'photo.jpg',
              attributes: [
                {
                  type: 'attribute',
                  string: 'Format',
                  tail: 'jpg',
                  attributes: [
                    {
                      type: 'attribute',
                      string: 'Media',
                      tail: 'electronic',
                      attributes: []
                    }
                  ]
                }
              ]
            }
          ]
        }
      ];

      const data = MultimediaDialogHelper.buildMultimediaDialogData(multimedias, 0);

      expect(data.files[0].sourceType).toBe('Electronic');
    });

    it('handles multiple files', () => {
      const multimedias: ApiAttribute[] = [
        {
          type: 'multimedia',
          string: 'Multimedia',
          tail: '',
          attributes: [
            {
              type: 'attribute',
              string: 'File',
              tail: 'photo1.jpg',
              attributes: []
            },
            {
              type: 'attribute',
              string: 'File',
              tail: 'photo2.png',
              attributes: []
            }
          ]
        }
      ];

      const data = MultimediaDialogHelper.buildMultimediaDialogData(multimedias, 0);

      expect(data.files.length).toBe(2);
    });

    it('handles file without format', () => {
      const multimedias: ApiAttribute[] = [
        {
          type: 'multimedia',
          string: 'Multimedia',
          tail: '',
          attributes: [
            {
              type: 'attribute',
              string: 'File',
              tail: 'unknown.xyz',
              attributes: []
            }
          ]
        }
      ];

      const data = MultimediaDialogHelper.buildMultimediaDialogData(multimedias, 0);

      expect(data.files[0].fileUrl).toBe('unknown.xyz');
      expect(data.files[0].format).toBeUndefined();
    });
  });

  describe('fileFormat()', () => {
    it('detects format from file extension', () => {
      const file: ApiAttribute = {
        type: 'attribute',
        string: 'File',
        tail: 'photo.jpg',
        attributes: []
      };

      const format = MultimediaDialogHelper.fileFormat(file);

      expect(format.toLowerCase()).toBe('jpg');
    });

    it('handles uppercase extension', () => {
      const file: ApiAttribute = {
        type: 'attribute',
        string: 'File',
        tail: 'document.PDF',
        attributes: []
      };

      const format = MultimediaDialogHelper.fileFormat(file);

      expect(format.toLowerCase()).toBe('pdf');
    });

    it('returns empty string for unknown format', () => {
      const file: ApiAttribute = {
        type: 'attribute',
        string: 'File',
        tail: 'file.unknown',
        attributes: []
      };

      const format = MultimediaDialogHelper.fileFormat(file);

      expect(format).toBe('');
    });
  });
});
