import { describe, it, expect, beforeEach, vi } from 'vitest';
import { LinkDialogActions } from './link-dialog-actions';
import { LinkDialogInterface, ApiObject, ApiAttribute, LinkItem, LinkDialogData } from '../models';

// Concrete implementation for testing the abstract class
class TestLinkDialogActions extends LinkDialogActions {
  constructor(labelString: any) {
    super(typeof labelString === 'function' ? labelString : (obj: ApiObject) => labelString);
  }

  onOpen(service: any, dialog: LinkDialogInterface, attributes: ApiAttribute[]): void {
    // Test implementation
  }

  onOK(data: LinkDialogData, attributes: ApiAttribute[], save: any): void {
    // Test implementation
  }
}

describe('LinkDialogActions', () => {
  let actions: LinkDialogActions;
  let mockDialog: LinkDialogInterface;

  beforeEach(() => {
    actions = new TestLinkDialogActions('Test Label');
    mockDialog = {
      data: {
        items: [],
        name: 'test',
        dataset: 'test-dataset',
        selected: []
      },
      objects: [] as ApiObject[]
    } as any;
  });

  describe('pushObject', () => {
    it('should add an item to dialog data items', () => {
      const mockObject: ApiObject = {
        type: 'person',
        string: 'P123',
        attributes: []
      };
      mockDialog.objects = [mockObject];

      actions.pushObject(1, mockDialog, mockObject);

      expect(mockDialog.data.items.length).toBe(1);
      expect(mockDialog.data.items[0].index).toBe(1);
      expect(mockDialog.data.items[0].id).toBe('P123');
    });

    it('should include label in the item', () => {
      const mockObject: ApiObject = {
        type: 'person',
        string: 'P456',
        attributes: []
      };
      mockDialog.objects = [mockObject];

      actions.pushObject(2, mockDialog, mockObject);

      expect(mockDialog.data.items[0].label).toContain('[P456]');
    });

    it('should handle multiple objects', () => {
      const obj1: ApiObject = {
        type: 'person',
        string: 'P1',
        attributes: []
      };
      const obj2: ApiObject = {
        type: 'person',
        string: 'P2',
        attributes: []
      };
      mockDialog.objects = [obj1, obj2];

      actions.pushObject(1, mockDialog, obj1);
      actions.pushObject(2, mockDialog, obj2);

      expect(mockDialog.data.items.length).toBe(2);
      expect(mockDialog.data.items[0].id).toBe('P1');
      expect(mockDialog.data.items[1].id).toBe('P2');
    });

    it('should use labelString function to generate label text', () => {
      const customLabelActions = new TestLinkDialogActions((obj) => `Custom: ${obj.string}`);
      const mockObject: ApiObject = {
        type: 'person',
        string: 'P789',
        attributes: []
      };
      mockDialog.objects = [mockObject];

      customLabelActions.pushObject(1, mockDialog, mockObject);

      expect(mockDialog.data.items[0].label).toContain('Custom:');
    });
  });

  describe('find', () => {
    it('should return label string when object is found', () => {
      const mockObject: ApiObject = {
        type: 'person',
        string: 'P123',
        attributes: []
      };
      mockDialog.objects = [mockObject];

      const result = actions.find('P123', mockDialog.objects);

      expect(result).toBeDefined();
    });

    it('should return undefined when object is not found', () => {
      mockDialog.objects = [];

      const result = actions.find('P999', mockDialog.objects);

      expect(result).toBeUndefined();
    });

    it('should find correct object from multiple objects', () => {
      const obj1: ApiObject = {
        type: 'person',
        string: 'P1',
        attributes: []
      };
      const obj2: ApiObject = {
        type: 'person',
        string: 'P2',
        attributes: []
      };
      const obj3: ApiObject = {
        type: 'person',
        string: 'P3',
        attributes: []
      };
      mockDialog.objects = [obj1, obj2, obj3];

      const result = actions.find('P2', mockDialog.objects);

      expect(result).toBeDefined();
    });

    it('should handle empty objects array', () => {
      const result = actions.find('P123', []);

      expect(result).toBeUndefined();
    });

    it('should use custom labelString function', () => {
      const customLabel = (obj: ApiObject) => `Name: ${obj.string}`;
      const customActions = new TestLinkDialogActions(customLabel);
      const mockObject: ApiObject = {
        type: 'person',
        string: 'P123',
        attributes: []
      };

      const result = customActions.find('P123', [mockObject]);

      expect(result).toContain('Name:');
    });
  });

  describe('spliceOutOne', () => {
    it('should remove one attribute from attributes array', () => {
      const attributes: ApiAttribute[] = [
        { type: 'noteLink', string: 'N1', tail: '', attributes: [] },
        { type: 'noteLink', string: 'N2', tail: '', attributes: [] },
        { type: 'noteLink', string: 'N3', tail: '', attributes: [] }
      ];
      const item: LinkItem = {
        index: 1,
        id: 'N2',
        label: 'Note 2'
      };

      actions.spliceOutOne(item, attributes);

      expect(attributes.length).toBe(2);
      expect(attributes[0].string).toBe('N1');
      expect(attributes[1].string).toBe('N3');
    });

    it('should remove the first matching attribute', () => {
      const attributes: ApiAttribute[] = [
        { type: 'noteLink', string: 'N1', tail: '', attributes: [] },
        { type: 'noteLink', string: 'N1', tail: '', attributes: [] }
      ];
      const item: LinkItem = {
        index: 1,
        id: 'N1',
        label: 'Note 1'
      };

      actions.spliceOutOne(item, attributes);

      expect(attributes.length).toBe(1);
    });

    it('should handle removing from beginning of array', () => {
      const attributes: ApiAttribute[] = [
        { type: 'noteLink', string: 'N1', tail: '', attributes: [] },
        { type: 'noteLink', string: 'N2', tail: '', attributes: [] }
      ];
      const item: LinkItem = {
        index: 0,
        id: 'N1',
        label: 'Note 1'
      };

      actions.spliceOutOne(item, attributes);

      expect(attributes.length).toBe(1);
      expect(attributes[0].string).toBe('N2');
    });

    it('should handle removing from end of array', () => {
      const attributes: ApiAttribute[] = [
        { type: 'noteLink', string: 'N1', tail: '', attributes: [] },
        { type: 'noteLink', string: 'N2', tail: '', attributes: [] }
      ];
      const item: LinkItem = {
        index: 1,
        id: 'N2',
        label: 'Note 2'
      };

      actions.spliceOutOne(item, attributes);

      expect(attributes.length).toBe(1);
      expect(attributes[0].string).toBe('N1');
    });

    it('should not modify array if item id is not found', () => {
      const attributes: ApiAttribute[] = [
        { type: 'noteLink', string: 'N1', tail: '', attributes: [] }
      ];
      const item: LinkItem = {
        index: 0,
        id: 'N999',
        label: 'Not Found'
      };

      actions.spliceOutOne(item, attributes);

      expect(attributes.length).toBe(1);
    });

    it('should handle empty attributes array', () => {
      const attributes: ApiAttribute[] = [];
      const item: LinkItem = {
        index: 0,
        id: 'N1',
        label: 'Note 1'
      };

      actions.spliceOutOne(item, attributes);

      expect(attributes.length).toBe(0);
    });
  });

  describe('constructor', () => {
    it('should initialize with labelString function', () => {
      const customLabel = (obj: ApiObject) => `Custom Label`;
      const customActions = new TestLinkDialogActions(customLabel);

      expect(customActions.labelString).toBeDefined();
    });

    it('should convert string labelString parameter to function', () => {
      const actions2 = new TestLinkDialogActions('Static Label');

      expect(typeof actions2.labelString).toBe('function');
      expect(actions2.labelString({} as ApiObject)).toBe('Static Label');
    });
  });

  describe('abstract methods', () => {
    it('should require onOpen implementation', () => {
      expect(typeof actions.onOpen).toBe('function');
    });

    it('should require onOK implementation', () => {
      expect(typeof actions.onOK).toBe('function');
    });
  });
});
