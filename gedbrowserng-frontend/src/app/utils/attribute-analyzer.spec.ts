import { describe, it, expect } from 'vitest';
import { AttributeAnalyzer } from './attribute-analyzer';

describe('AttributeAnalyzer', () => {
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

  describe('label()', () => {
    it('returns string for attribute type', () => {
      const parent = createMockParent('attribute', 'Custom Label');
      const analyzer = new AttributeAnalyzer(parent);
      expect(analyzer.label()).toBe('Custom Label');
    });

    it('returns titleCase for non-attribute types', () => {
      const parent = createMockParent('name');
      const analyzer = new AttributeAnalyzer(parent);
      expect(analyzer.label()).toBe('Name');
    });

    it('titleCases multimedia', () => {
      const parent = createMockParent('multimedia');
      const analyzer = new AttributeAnalyzer(parent);
      expect(analyzer.label()).toBe('Multimedia');
    });
  });

  describe('contents()', () => {
    it('returns tail for attribute type', () => {
      const parent = createMockParent('attribute', 'Label', 'Content Value');
      const analyzer = new AttributeAnalyzer(parent);
      expect(analyzer.contents()).toBe('Content Value');
    });

    it('returns cleaned up string for name type', () => {
      const parent = createMockParent('name', 'John/Doe');
      const analyzer = new AttributeAnalyzer(parent);
      expect(analyzer.contents()).toContain('John');
    });

    it('returns empty string for multimedia type', () => {
      const parent = createMockParent('multimedia');
      const analyzer = new AttributeAnalyzer(parent);
      expect(analyzer.contents()).toBe('');
    });

    it('returns string for other types', () => {
      const parent = createMockParent('birthdate', 'January 1, 2000');
      const analyzer = new AttributeAnalyzer(parent);
      expect(analyzer.contents()).toBe('January 1, 2000');
    });
  });

  describe('editable()', () => {
    it('returns false for Reference Number', () => {
      const parent = createMockParent('attribute', 'Reference Number');
      const analyzer = new AttributeAnalyzer(parent);
      expect(analyzer.editable()).toBe(false);
    });

    it('returns false for Changed', () => {
      const parent = createMockParent('attribute', 'Changed');
      const analyzer = new AttributeAnalyzer(parent);
      expect(analyzer.editable()).toBe(false);
    });

    it('returns true for other attributes', () => {
      const parent = createMockParent('attribute', 'Custom Field');
      const analyzer = new AttributeAnalyzer(parent);
      expect(analyzer.editable()).toBe(true);
    });

    it('returns true for name type', () => {
      const parent = createMockParent('name');
      const analyzer = new AttributeAnalyzer(parent);
      expect(analyzer.editable()).toBe(true);
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
    it('returns sourcelink href', () => {
      const parent = createMockParent('sourcelink', 'source123');
      const analyzer = new AttributeAnalyzer(parent);
      expect(analyzer.href()).toBe('#/test/sources/source123');
    });

    it('returns submitterlink href', () => {
      const parent = createMockParent('submitterlink', 'submitter456');
      const analyzer = new AttributeAnalyzer(parent);
      expect(analyzer.href()).toBe('#/test/submitters/submitter456');
    });

    it('returns submissionlink href', () => {
      const parent = createMockParent('submissionlink', 'submission789');
      const analyzer = new AttributeAnalyzer(parent);
      expect(analyzer.href()).toBe('#/test/submissions/submission789');
    });

    it('returns notelink href', () => {
      const parent = createMockParent('notelink', 'note123');
      const analyzer = new AttributeAnalyzer(parent);
      expect(analyzer.href()).toBe('#/test/notes/note123');
    });

    it('returns tail for File attribute', () => {
      const parent = createMockParent('attribute', 'File', '/path/to/file.pdf');
      const analyzer = new AttributeAnalyzer(parent);
      expect(analyzer.href()).toBe('/path/to/file.pdf');
    });

    it('returns null for non-link types', () => {
      const parent = createMockParent('name', 'John Doe');
      const analyzer = new AttributeAnalyzer(parent);
      expect(analyzer.href()).toBeNull();
    });
  });

  describe('hrefit()', () => {
    it('constructs sourcelink correctly with dataset', () => {
      const parent = createMockParent('sourcelink', 'source123', '', 'mydataset');
      const analyzer = new AttributeAnalyzer(parent);
      const href = analyzer.hrefit('mydataset', parent.attribute);
      expect(href).toBe('#/mydataset/sources/source123');
    });

    it('constructs submitterlink correctly', () => {
      const parent = createMockParent('submitterlink', 'sub456', '', 'dataset2');
      const analyzer = new AttributeAnalyzer(parent);
      const href = analyzer.hrefit('dataset2', parent.attribute);
      expect(href).toBe('#/dataset2/submitters/sub456');
    });

    it('handles empty string in sourcelink', () => {
      const parent = createMockParent('sourcelink', '', '', 'ds');
      const analyzer = new AttributeAnalyzer(parent);
      const href = analyzer.hrefit('ds', parent.attribute);
      expect(href).toBe('#/ds/sources/');
    });
  });

  describe('multimedia()', () => {
    it('returns true for multimedia type', () => {
      const parent = createMockParent('multimedia');
      const analyzer = new AttributeAnalyzer(parent);
      expect(analyzer.multimedia()).toBe(true);
    });

    it('returns false for non-multimedia types', () => {
      const parent = createMockParent('name');
      const analyzer = new AttributeAnalyzer(parent);
      expect(analyzer.multimedia()).toBe(false);
    });

    it('returns false for attribute type', () => {
      const parent = createMockParent('attribute', 'Custom');
      const analyzer = new AttributeAnalyzer(parent);
      expect(analyzer.multimedia()).toBe(false);
    });
  });
});
