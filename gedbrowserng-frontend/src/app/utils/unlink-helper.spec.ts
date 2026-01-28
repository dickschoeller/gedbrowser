import { describe, it, expect, vi } from 'vitest';
import { UnlinkHelper } from './unlink-helper';
import { LinkDialogInterface } from '../interfaces';
import { LinkDialogData, ApiAttribute } from '../models';

describe('UnlinkHelper', () => {
  let unlinkHelper: UnlinkHelper;
  const mockComparator = (a: any, b: any) => a.name.localeCompare(b.name);
  const mockLabelFunction = vi.fn((attr: any) => `Label: ${attr.name}`);

  beforeEach(() => {
    unlinkHelper = new UnlinkHelper(mockLabelFunction, mockComparator, 'sourcelink');
  });

  describe('constructor', () => {
    it('initializes with label, comparator, and link string', () => {
      const helper = new UnlinkHelper(mockLabelFunction, mockComparator, 'submitterlink');
      expect(helper.labelString).toBeDefined();
    });
  });

  describe('fillUnlinkData()', () => {
    it('filters attributes by linkString type', () => {
      const mockDialog = {
        data: { items: [] }
      } as LinkDialogInterface;

      const attributes: ApiAttribute[] = [
        { type: 'sourcelink', string: 'source1', tail: 'Source 1' },
        { type: 'name', string: 'John', tail: '' },
        { type: 'sourcelink', string: 'source2', tail: 'Source 2' }
      ];

      unlinkHelper.fillUnlinkData(mockDialog, [], attributes);
      expect(mockDialog.data.items.length).toBeGreaterThan(0);
    });

    it('ignores attributes with different type', () => {
      const mockDialog = {
        data: { items: [] }
      } as LinkDialogInterface;

      const attributes: ApiAttribute[] = [
        { type: 'name', string: 'John', tail: '' },
        { type: 'birthdate', string: '2000', tail: '' }
      ];

      unlinkHelper.fillUnlinkData(mockDialog, [], attributes);
      expect(mockDialog.data.items.length).toBe(0);
    });

    it('sorts objects using comparator', () => {
      const mockDialog = {
        data: { items: [] }
      } as LinkDialogInterface;

      const mockObjects = [
        { id: '2', name: 'Zebra' },
        { id: '1', name: 'Alpha' }
      ];

      unlinkHelper.fillUnlinkData(mockDialog, mockObjects, []);
      expect(mockDialog.objects[0].name).toBe('Alpha');
      expect(mockDialog.objects[1].name).toBe('Zebra');
    });

    it('handles empty attributes array', () => {
      const mockDialog = {
        data: { items: [] }
      } as LinkDialogInterface;

      unlinkHelper.fillUnlinkData(mockDialog, [], []);
      expect(mockDialog.data.items.length).toBe(0);
    });
  });

  describe('onOK()', () => {
    it('removes selected items from attributes', () => {
      const mockData = {
        selected: [{ id: 'source1', name: 'Source 1' }]
      } as LinkDialogData;

      const attributes: ApiAttribute[] = [
        { type: 'sourcelink', string: 'source1', tail: 'Source 1', attributes: [] },
        { type: 'sourcelink', string: 'source2', tail: 'Source 2', attributes: [] }
      ];

      const mockSave = vi.fn();
      unlinkHelper.onOK(mockData, attributes, mockSave);

      const sourcelinks = attributes.filter((a) => a.type === 'sourcelink');
      expect(sourcelinks.length).toBe(1);
    });

    it('handles empty selected items', () => {
      const mockData = {
        selected: []
      } as LinkDialogData;

      const attributes: ApiAttribute[] = [
        { type: 'sourcelink', string: 'source1', tail: 'Source 1', attributes: [] }
      ];

      const mockSave = vi.fn();
      const initialLength = attributes.length;
      unlinkHelper.onOK(mockData, attributes, mockSave);
      expect(attributes.length).toBe(initialLength);
    });
  });
});
