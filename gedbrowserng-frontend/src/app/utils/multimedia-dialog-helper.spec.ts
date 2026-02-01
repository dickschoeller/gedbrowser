import { describe, it, expect } from 'vitest';
import { MultimediaDialogHelper } from './multimedia-dialog-helper';
import { MultimediaDialogData, ApiAttribute } from '../models';

const makeDialogData = (
  overrides: Partial<MultimediaDialogData> = {}
): MultimediaDialogData => ({
  title: 'Photos',
  files: [],
  note: '',
  ...overrides
});

const makeFile = (fileUrl: string, format = 'jpg', sourceType = 'electronic') => ({
  fileUrl,
  format: format as any,
  sourceType: sourceType as any
});

const makeMultimediaAttribute = (attributes: ApiAttribute[]): ApiAttribute => ({
  type: 'multimedia',
  string: 'Multimedia',
  tail: '',
  attributes
});

describe('MultimediaDialogHelper', () => {
  describe('buildMultimediaAttribute()', () => {
    it('creates multimedia attribute with title and files', () => {
      const data = makeDialogData({
        title: 'Photo Album',
        files: [makeFile('photo1.jpg')],
        note: 'Family photos'
      });

      const attribute = MultimediaDialogHelper.buildMultimediaAttribute(data);

      expect(attribute.type).toBe('multimedia');
      expect(attribute.string).toBe('Multimedia');
      expect(attribute.attributes.length).toBeGreaterThan(1);
    });

    it('includes title in first attribute', () => {
      const data = makeDialogData({ title: 'My Title' });

      const attribute = MultimediaDialogHelper.buildMultimediaAttribute(data);
      const titleAttr = attribute.attributes.find((a: any) => a.string === 'Title');

      expect(titleAttr).toBeDefined();
      expect(titleAttr.tail).toBe('My Title');
    });

    it('includes all files', () => {
      const data = makeDialogData({
        files: [makeFile('photo1.jpg', 'jpg'), makeFile('photo2.png', 'png')]
      });

      const attribute = MultimediaDialogHelper.buildMultimediaAttribute(data);
      const fileAttrs = attribute.attributes.filter((a: any) => a.string === 'File');

      expect(fileAttrs.length).toBe(2);
    });

    it.each([
      ['Important photos', true],
      ['', false]
    ])('handles note value "%s"', (note, shouldInclude) => {
      const attribute = MultimediaDialogHelper.buildMultimediaAttribute(
        makeDialogData({ note })
      );
      const noteAttrs = attribute.attributes.filter((a: any) => a.string === 'Note');

      if (shouldInclude) {
        expect(noteAttrs.length).toBe(1);
        expect(noteAttrs[0].tail).toBe(note);
      } else {
        expect(noteAttrs.length).toBe(0);
      }
    });

    it('handles file with source type', () => {
      const data = makeDialogData({
        files: [makeFile('photo.jpg', 'jpg', 'electronic')]
      });

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
        makeMultimediaAttribute([
          { type: 'attribute', string: 'Title', tail: 'Family Photos', attributes: [] }
        ])
      ];

      const data = MultimediaDialogHelper.buildMultimediaDialogData(multimedias, 0);

      expect(data.title).toBe('Family Photos');
    });

    it('extracts files from multimedia', () => {
      const multimedias: ApiAttribute[] = [
        makeMultimediaAttribute([
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
        ])
      ];

      const data = MultimediaDialogHelper.buildMultimediaDialogData(multimedias, 0);

      expect(data.files.length).toBe(1);
      expect(data.files[0].fileUrl).toBe('photo.jpg');
    });

    it('extracts note from multimedia', () => {
      const multimedias: ApiAttribute[] = [
        makeMultimediaAttribute([
          { type: 'attribute', string: 'Note', tail: 'Some note', attributes: [] }
        ])
      ];

      const data = MultimediaDialogHelper.buildMultimediaDialogData(multimedias, 0);

      expect(data.note).toBe('Some note');
    });

    it('extracts format from file attribute', () => {
      const multimedias: ApiAttribute[] = [
        makeMultimediaAttribute([
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
        ])
      ];

      const data = MultimediaDialogHelper.buildMultimediaDialogData(multimedias, 0);

      expect(data.files[0].format).toBe('jpg');
    });

    it('extracts source type from file attribute', () => {
      const multimedias: ApiAttribute[] = [
        makeMultimediaAttribute([
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
        ])
      ];

      const data = MultimediaDialogHelper.buildMultimediaDialogData(multimedias, 0);

      expect(data.files[0].sourceType).toBe('Electronic');
    });

    it('handles multiple files', () => {
      const multimedias: ApiAttribute[] = [
        makeMultimediaAttribute([
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
        ])
      ];

      const data = MultimediaDialogHelper.buildMultimediaDialogData(multimedias, 0);

      expect(data.files.length).toBe(2);
    });

    it('handles file without format', () => {
      const multimedias: ApiAttribute[] = [
        makeMultimediaAttribute([
          {
            type: 'attribute',
            string: 'File',
            tail: 'unknown.xyz',
            attributes: []
          }
        ])
      ];

      const data = MultimediaDialogHelper.buildMultimediaDialogData(multimedias, 0);

      expect(data.files[0].fileUrl).toBe('unknown.xyz');
      expect(data.files[0].format).toBeUndefined();
    });
  });

  describe('fileFormat()', () => {
    it.each([
      ['photo.jpg', 'jpg'],
      ['document.PDF', 'pdf'],
      ['file.unknown', '']
    ])('handles file format for %s', (tail, expected) => {
      const file: ApiAttribute = {
        type: 'attribute',
        string: 'File',
        tail,
        attributes: []
      };

      const format = MultimediaDialogHelper.fileFormat(file);

      if (expected) {
        expect(format.toLowerCase()).toBe(expected);
      } else {
        expect(format).toBe('');
      }
    });
  });
});
