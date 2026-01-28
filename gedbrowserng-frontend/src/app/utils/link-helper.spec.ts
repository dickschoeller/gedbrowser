import { describe, it, expect, vi } from 'vitest';
import { LinkHelper } from './link-helper';
import { LinkDialogInterface } from '../interfaces';
import { LinkDialogData, ApiAttribute } from '../models';

describe('LinkHelper', () => {
  let linkHelper: LinkHelper;
  const mockComparator = (a: any, b: any) => a.name.localeCompare(b.name);
  const mockLabelFunction = vi.fn((attr: any) => `Label: ${attr.name}`);

  beforeEach(() => {
    linkHelper = new LinkHelper(mockLabelFunction, mockComparator, 'sourcelink');
  });

  describe('constructor', () => {
    it('initializes with label, comparator, and link string', () => {
      const helper = new LinkHelper(mockLabelFunction, mockComparator, 'submitterlink');
      expect(helper.labelString).toBeDefined();
    });
  });

  describe('fillLinkData()', () => {
    it('populates dialog objects and items', () => {
      const mockDialog = {
        data: { items: [] }
      } as LinkDialogInterface;

      const mockObjects = [
        { id: '2', name: 'Beta' },
        { id: '1', name: 'Alpha' }
      ] as any[];

      linkHelper.fillLinkData(mockDialog, mockObjects);
      expect(mockDialog.objects).toBeDefined();
      expect(mockDialog.data.items.length).toBeGreaterThan(0);
    });

    it('sorts objects using provided comparator', () => {
      const mockDialog = {
        data: { items: [] }
      } as LinkDialogInterface;

      const mockObjects = [
        { id: '2', name: 'Zebra' },
        { id: '1', name: 'Alpha' }
      ] as any[];

      linkHelper.fillLinkData(mockDialog, mockObjects);
      expect(mockDialog.objects[0].name).toBe('Alpha');
      expect(mockDialog.objects[1].name).toBe('Zebra');
    });

    it('handles empty objects list', () => {
      const mockDialog = {
        data: { items: [] }
      } as LinkDialogInterface;

      linkHelper.fillLinkData(mockDialog, []);
      expect(mockDialog.objects.length).toBe(0);
      expect(mockDialog.data.items.length).toBe(0);
    });
  });

  describe('onOK()', () => {
    it('creates link attributes for selected items', () => {
      const mockData = {
        selected: [
          { id: 'source1', name: 'Source 1' },
          { id: 'source2', name: 'Source 2' }
        ]
      } as LinkDialogData;

      const attributes: ApiAttribute[] = [];
      const mockSave1 = vi.fn();
      linkHelper.onOK(mockData, attributes, mockSave1);

      expect(mockSave1).toHaveBeenCalled();
      expect(attributes.length).toBe(2);
      expect(attributes[0].type).toBe('sourcelink');
      expect(attributes[0].string).toBe('source1');
    });

    it('creates empty attributes array for each link', () => {
      const mockData = {
        selected: [{ id: 'source1', name: 'Source 1' }]
      } as LinkDialogData;

      const attributes: ApiAttribute[] = [];
      const mockSave2 = vi.fn();
      linkHelper.onOK(mockData, attributes, mockSave2);

      expect(attributes[0].attributes).toBeDefined();
      expect(Array.isArray(attributes[0].attributes)).toBe(true);
    });

    it('handles empty selected items', () => {
      const mockData = {
        selected: []
      } as LinkDialogData;

      const attributes: ApiAttribute[] = [];
      const mockSave3 = vi.fn();
      linkHelper.onOK(mockData, attributes, mockSave3);

      expect(attributes.length).toBe(0);
    });

    it('sets tail to empty string for links', () => {
      const mockData = {
        selected: [{ id: 'id1', name: 'Name' }]
      } as LinkDialogData;

      const attributes: ApiAttribute[] = [];
      const mockSave4 = vi.fn();
      linkHelper.onOK(mockData, attributes, mockSave4);

      expect(attributes[0].tail).toBe('');
    });
  });
});
