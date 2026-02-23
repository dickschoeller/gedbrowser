import { describe, it, expect } from 'vitest';
import { AttributeAnalyzer } from './attribute-analyzer';

function createMockParent(attributeType: string, attributeString = '', attributeTail = '', dataset = 'test') {
  return {
    attribute: {
      type: attributeType,
      string: attributeString,
      tail: attributeTail
    },
    attributes: [
      { type: attributeType, string: attributeString, tail: attributeTail }
    ],
    dataset
  };
}

describe('AttributeAnalyzer', () => {

  describe('label()', () => {
    it.each([
      ['attribute', 'Custom Label', '', 'Custom Label'],
      ['name', '', '', 'Name'],
      ['multimedia', '', '', 'Multimedia']
    ])('returns label for %s', (type, stringValue, tail, expected) => {
      const parent = createMockParent(type, stringValue, tail);
      const analyzer = new AttributeAnalyzer(parent);
      expect(analyzer.label()).toBe(expected);
    });
  });

  describe('contents()', () => {
    it.each([
      ['attribute', 'Label', 'Content Value', 'Content Value', 'toBe'],
      ['name', 'John/Doe', '', 'John', 'toContain'],
      ['multimedia', '', '', '', 'toBe'],
      ['birthdate', 'January 1, 2000', '', 'January 1, 2000', 'toBe']
    ])('returns contents for %s', (type, stringValue, tail, expected, matcher) => {
      const parent = createMockParent(type, stringValue, tail);
      const analyzer = new AttributeAnalyzer(parent);
      if (matcher === 'toContain') {
        expect(analyzer.contents()).toContain(expected);
      } else {
        expect(analyzer.contents()).toBe(expected);
      }
    });
  });

  describe('editable()', () => {
    it.each([
      ['attribute', 'Reference Number', false],
      ['attribute', 'Changed', false],
      ['attribute', 'Custom Field', true],
      ['name', '', true]
    ])('returns editable=%s for %s', (type, stringValue, expected) => {
      const parent = createMockParent(type, stringValue);
      const analyzer = new AttributeAnalyzer(parent);
      expect(analyzer.editable()).toBe(expected);
    });
  });

  describe('first()', () => {
    it('returns true when attribute is first in list', () => {
      const parent = createMockParent('name');
      parent.attributes = [parent.attribute, { type: 'other', string: '', tail: '' }];
      const analyzer = new AttributeAnalyzer(parent);
      expect(analyzer.first()).toBe(true);
    });

    it('returns false when attribute is not first', () => {
      const parent = createMockParent('name');
      const firstAttr = { type: 'other', string: '', tail: '' };
      parent.attributes = [firstAttr, parent.attribute];
      const analyzer = new AttributeAnalyzer(parent);
      expect(analyzer.first()).toBe(false);
    });
  });

  describe('last()', () => {
    it('returns true when attribute is last in list', () => {
      const parent = createMockParent('name');
      parent.attributes = [parent.attribute];
      const analyzer = new AttributeAnalyzer(parent);
      expect(analyzer.last()).toBe(true);
    });

    it('returns true when last non-metadata attribute', () => {
      const parent = createMockParent('name');
      const refNum = { type: 'attribute', string: 'Reference Number', tail: '123' };
      parent.attributes = [parent.attribute, refNum];
      const analyzer = new AttributeAnalyzer(parent);
      expect(analyzer.last()).toBe(true);
    });

    it('returns true with both Reference Number and Changed', () => {
      const parent = createMockParent('name');
      const refNum = { type: 'attribute', string: 'Reference Number', tail: '123' };
      const changed = { type: 'attribute', string: 'Changed', tail: '2024' };
      parent.attributes = [parent.attribute, refNum, changed];
      const analyzer = new AttributeAnalyzer(parent);
      expect(analyzer.last()).toBe(true);
    });

    it('returns false when not last', () => {
      const parent = createMockParent('name');
      const other = { type: 'birthdate', string: '', tail: '' };
      parent.attributes = [parent.attribute, other];
      const analyzer = new AttributeAnalyzer(parent);
      expect(analyzer.last()).toBe(false);
    });
  });

  describe('isLast()', () => {
    it('returns true when attribute is last', () => {
      const parent = createMockParent('name');
      const attr = parent.attribute;
      const analyzer = new AttributeAnalyzer(parent);
      expect(analyzer.isLast([attr], attr)).toBe(true);
    });

    it('returns true with Reference Number as last', () => {
      const parent = createMockParent('name');
      const attr = parent.attribute;
      const refNum = { type: 'attribute', string: 'Reference Number', tail: '' };
      const analyzer = new AttributeAnalyzer(parent);
      expect(analyzer.isLast([attr, refNum], attr)).toBe(true);
    });

    it('returns true with Changed as last', () => {
      const parent = createMockParent('name');
      const attr = parent.attribute;
      const changed = { type: 'attribute', string: 'Changed', tail: '' };
      const analyzer = new AttributeAnalyzer(parent);
      expect(analyzer.isLast([attr, changed], attr)).toBe(true);
    });

    it('handles both Reference Number and Changed', () => {
      const parent = createMockParent('name');
      const attr = parent.attribute;
      const refNum = { type: 'attribute', string: 'Reference Number', tail: '' };
      const changed = { type: 'attribute', string: 'Changed', tail: '' };
      const analyzer = new AttributeAnalyzer(parent);
      expect(analyzer.isLast([attr, refNum, changed], attr)).toBe(true);
    });

    it('returns false when clearly not last', () => {
      const parent = createMockParent('name');
      const attr = parent.attribute;
      const other = { type: 'birthdate', string: '', tail: '' };
      const analyzer = new AttributeAnalyzer(parent);
      expect(analyzer.isLast([attr, other], attr)).toBe(false);
    });
  });

  describe('lastIndex()', () => {
    it('returns 0 for null parent', () => {
      const analyzer = new AttributeAnalyzer(null);
      expect(analyzer.lastIndex()).toBe(0);
    });

    it('returns 0 for single attribute', () => {
      const parent = createMockParent('name');
      const analyzer = new AttributeAnalyzer(parent);
      expect(analyzer.lastIndex()).toBe(0);
    });

    it('finds index with Reference Number as last', () => {
      const parent = createMockParent('name');
      const attr = parent.attribute;
      const refNum = { type: 'attribute', string: 'Reference Number', tail: '' };
      parent.attributes = [attr, refNum];
      const analyzer = new AttributeAnalyzer(parent);
      expect(analyzer.lastIndex()).toBe(0);
    });
  });

  describe('href()', () => {
    it.each([
      ['sourcelink', 'source123', '', '#/test/sources/source123'],
      ['submitterlink', 'submitter456', '', '#/test/submitters/submitter456'],
      ['submissionlink', 'submission789', '', '#/test/submissions/submission789'],
      ['notelink', 'note123', '', '#/test/notes/note123'],
      ['attribute', 'File', '/path/to/file.pdf', '/path/to/file.pdf']
    ])('returns href for %s', (type, stringValue, tail, expected) => {
      const parent = createMockParent(type, stringValue, tail);
      const analyzer = new AttributeAnalyzer(parent);
      expect(analyzer.href()).toBe(expected);
    });

    it('returns null for non-link types', () => {
      const parent = createMockParent('name', 'John Doe');
      const analyzer = new AttributeAnalyzer(parent);
      expect(analyzer.href()).toBeNull();
    });
  });

  describe('hrefit()', () => {
    it.each([
      ['sourcelink', 'source123', 'mydataset', '#/mydataset/sources/source123'],
      ['submitterlink', 'sub456', 'dataset2', '#/dataset2/submitters/sub456']
    ])('constructs %s with dataset', (type, stringValue, dataset, expected) => {
      const parent = createMockParent(type, stringValue, '', dataset);
      const analyzer = new AttributeAnalyzer(parent);
      const href = analyzer.hrefit(dataset, parent.attribute);
      expect(href).toBe(expected);
    });

    it('handles empty string in sourcelink', () => {
      const parent = createMockParent('sourcelink', '', '', 'ds');
      const analyzer = new AttributeAnalyzer(parent);
      const href = analyzer.hrefit('ds', parent.attribute);
      expect(href).toBe('#/ds/sources/');
    });
  });

  describe('multimedia()', () => {
    it.each([
      [true, 'multimedia', '', ''],
      [false, 'name', '', ''],
      [false, 'attribute', 'Custom', " with stringValue 'Custom'"]
    ])('returns %s for type \'%s\'%s', (expected, type, stringValue, suffix) => {
      const parent = createMockParent(type, stringValue);
      const analyzer = new AttributeAnalyzer(parent);
      expect(analyzer.multimedia()).toBe(expected);
    });
  });
});
