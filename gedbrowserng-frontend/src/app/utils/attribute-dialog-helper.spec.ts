import { describe, it, expect, beforeEach } from 'vitest';
import { AttributeDialogHelper } from './attribute-dialog-helper';
import { AttributeDialogData } from '../models';

describe('AttributeDialogHelper', () => {
  let helper: AttributeDialogHelper;
  let mockParent: any;

  beforeEach(() => {
    mockParent = {
      attribute: {
        type: 'name',
        string: 'John Doe',
        tail: '',
        attributes: []
      },
      index: 0
    };
    helper = new AttributeDialogHelper(mockParent);
  });

  describe('dialogData()', () => {
    it('creates dialog data with given type', () => {
      const data = AttributeDialogHelper.dialogData('NAME');
      expect(data.type).toBe('NAME');
      expect(data.insert).toBe(true);
      expect(data.index).toBe(0);
      expect(data.text).toBe('');
    });

    it('initializes all fields with empty values', () => {
      const data = AttributeDialogHelper.dialogData('Custom');
      expect(data.type).toBe('Custom');
      expect(data.text).toBe('');
      expect(data.date).toBe('');
      expect(data.place).toBe('');
      expect(data.note).toBe('');
      expect(data.originalType).toBe('');
    });
  });

  describe('buildData()', () => {
    it('builds data for name type attribute', () => {
      mockParent.attribute.type = 'name';
      mockParent.attribute.string = 'John Doe';
      mockParent.index = 5;

      const data = helper.buildData(true);

      expect(data.type).toBe('Name');
      expect(data.text).toBe('John Doe');
      expect(data.insert).toBe(true);
      expect(data.index).toBe(5);
    });

    it('builds data for attribute type with string and tail', () => {
      mockParent.attribute.type = 'attribute';
      mockParent.attribute.string = 'Birth Date';
      mockParent.attribute.tail = '1 January 2000';

      const data = helper.buildData(false);

      expect(data.type).toBe('Birth Date');
      expect(data.text).toBe('1 January 2000');
      expect(data.insert).toBe(false);
    });

    it('extracts date from attributes', () => {
      mockParent.attribute.attributes = [
        { type: 'date', string: '1 JAN 2000', tail: '' }
      ];

      const data = helper.buildData(true);

      expect(data.date).toBe('1 JAN 2000');
    });

    it('extracts place from attributes', () => {
      mockParent.attribute.attributes = [
        { type: 'place', string: 'Springfield', tail: '' }
      ];

      const data = helper.buildData(true);

      expect(data.place).toBe('Springfield');
    });

    it('extracts note from attributes by string', () => {
      mockParent.attribute.attributes = [
        { type: 'attribute', string: 'Note', tail: 'Some note text' }
      ];

      const data = helper.buildData(true);

      expect(data.note).toBe('Some note text');
    });

    it('handles missing attributes gracefully', () => {
      mockParent.attribute.attributes = [];

      const data = helper.buildData(true);

      expect(data.date).toBeUndefined();
      expect(data.place).toBeUndefined();
      expect(data.note).toBeUndefined();
    });
  });

  describe('populateParentAttribute()', () => {
    it('populates parent attribute with data', () => {
      const data: AttributeDialogData = {
        insert: true,
        index: 0,
        type: 'Name',
        text: 'Jane Doe',
        date: '',
        place: '',
        note: '',
        originalType: 'Name',
        originalText: 'John Doe',
        originalDate: '',
        originalPlace: '',
        originalNote: ''
      };

      helper.populateParentAttribute(data);

      expect(mockParent.attribute.type).toBe('name');
      expect(mockParent.attribute.string).toBe('Jane Doe');
    });
  });

  describe('populateNewAttribute()', () => {
    it('creates new attribute with data', () => {
      const data: AttributeDialogData = {
        insert: true,
        index: 0,
        type: 'Birth Date',
        text: '1 JAN 2000',
        date: '1 JAN 2000',
        place: 'Springfield',
        note: 'Note text',
        originalType: 'Birth Date',
        originalText: '1 JAN 2000',
        originalDate: '1 JAN 2000',
        originalPlace: 'Springfield',
        originalNote: 'Note text'
      };

      const attribute = helper.populateNewAttribute(data);

      expect(attribute.type).toBe('attribute');
      expect(attribute.string).toBe('Birth Date');
      expect(attribute.attributes).toBeDefined();
      expect(Array.isArray(attribute.attributes)).toBe(true);
    });

    it('creates name attribute with lowercase type', () => {
      const data: AttributeDialogData = {
        insert: true,
        index: 0,
        type: 'Name',
        text: 'John Doe',
        date: '',
        place: '',
        note: '',
        originalType: 'Name',
        originalText: 'John Doe',
        originalDate: '',
        originalPlace: '',
        originalNote: ''
      };

      const attribute = helper.populateNewAttribute(data);

      expect(attribute.type).toBe('name');
      expect(attribute.string).toBe('John Doe');
    });
  });

  describe('simpleAttribute()', () => {
    it('creates simple attribute with type and text', () => {
      const attribute = helper.simpleAttribute('Name', 'Jane Smith');

      expect(attribute.type).toBe('name');
      expect(attribute.string).toBe('Jane Smith');
      expect(attribute.tail).toBe('');
      expect(attribute.attributes).toBeDefined();
      expect(Array.isArray(attribute.attributes)).toBe(true);
    });

    it('creates attribute type for non-name', () => {
      const attribute = helper.simpleAttribute('Birth Date', '1 JAN 2000');

      expect(attribute.type).toBe('attribute');
      expect(attribute.string).toBe('Birth Date');
      expect(attribute.tail).toBe('1 JAN 2000');
    });

    it('handles empty text', () => {
      const attribute = helper.simpleAttribute('Name', '');

      expect(attribute.type).toBe('name');
      expect(attribute.string).toBe('');
    });
  });

  describe('private methods (via public interfaces)', () => {
    it('handles date attribute addition', () => {
      const data: AttributeDialogData = {
        insert: true,
        index: 0,
        type: 'Birth Date',
        text: '',
        date: '1 JAN 2000',
        place: '',
        note: '',
        originalType: 'Birth Date',
        originalText: '',
        originalDate: '',
        originalPlace: '',
        originalNote: ''
      };

      const attribute = helper.populateNewAttribute(data);
      const dateAttrs = attribute.attributes.filter((a: any) => a.type === 'date');

      expect(dateAttrs.length).toBe(1);
      expect(dateAttrs[0].string).toBe('1 JAN 2000');
    });

    it('handles place attribute addition', () => {
      const data: AttributeDialogData = {
        insert: true,
        index: 0,
        type: 'Birth Place',
        text: '',
        date: '',
        place: 'Springfield',
        note: '',
        originalType: 'Birth Place',
        originalText: '',
        originalDate: '',
        originalPlace: '',
        originalNote: ''
      };

      const attribute = helper.populateNewAttribute(data);
      const placeAttrs = attribute.attributes.filter((a: any) => a.type === 'place');

      expect(placeAttrs.length).toBe(1);
      expect(placeAttrs[0].string).toBe('Springfield');
    });

    it('handles note attribute addition', () => {
      const data: AttributeDialogData = {
        insert: true,
        index: 0,
        type: 'Custom',
        text: '',
        date: '',
        place: '',
        note: 'Some note',
        originalType: 'Custom',
        originalText: '',
        originalDate: '',
        originalPlace: '',
        originalNote: ''
      };

      const attribute = helper.populateNewAttribute(data);
      const noteAttrs = attribute.attributes.filter((a: any) => a.string === 'Note');

      expect(noteAttrs.length).toBe(1);
      expect(noteAttrs[0].tail).toBe('Some note');
    });

    it('deletes empty date attributes', () => {
      mockParent.attribute.attributes = [
        { type: 'date', string: '1 JAN 2000', tail: '' }
      ];

      const data = helper.buildData(true);
      data.date = ''; // Clear the date

      helper.populateParentAttribute(data);

      const dateAttrs = mockParent.attribute.attributes.filter((a: any) => a.type === 'date');
      expect(dateAttrs.length).toBe(0);
    });

    it('updates existing date attribute', () => {
      mockParent.attribute.attributes = [
        { type: 'date', string: '1 JAN 2000', tail: '' }
      ];

      const data: AttributeDialogData = {
        insert: true,
        index: 0,
        type: 'Birth Date',
        text: '',
        date: '2 FEB 2001',
        place: '',
        note: '',
        originalType: 'Birth Date',
        originalText: '',
        originalDate: '1 JAN 2000',
        originalPlace: '',
        originalNote: ''
      };

      helper.populateParentAttribute(data);

      const dateAttrs = mockParent.attribute.attributes.filter((a: any) => a.type === 'date');
      expect(dateAttrs[0].string).toBe('2 FEB 2001');
    });
  });
});
