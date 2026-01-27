import { describe, it, expect, beforeEach, vi } from 'vitest';
import { LinkDialogLauncher } from './link-dialog-launcher';
import { LinkDialogActions } from './link-dialog-actions';
import { LinkDialogData } from '../models';
import { of } from 'rxjs';

describe('LinkDialogLauncher', () => {
  let mockThat: any;
  let mockDialogRef: any;
  let mockLinkHelper: any;

  beforeEach(() => {
    mockDialogRef = {
      componentInstance: {},
      afterOpened: vi.fn(),
      afterClosed: vi.fn()
    };

    mockThat = {
      dialog: {
        open: vi.fn().mockReturnValue(mockDialogRef)
      },
      dataset: 'test-dataset',
      service: {
        getAll: vi.fn()
      },
      parent: {
        attributes: [],
        save: vi.fn()
      }
    };

    mockLinkHelper = {
      onOpen: vi.fn(),
      onOK: vi.fn()
    };
  });

  describe('openDialog()', () => {
    it('opens dialog with correct parameters', () => {
      mockDialogRef.afterOpened.mockReturnValue(of(null));
      mockDialogRef.afterClosed.mockReturnValue(of(null));

      LinkDialogLauncher.openDialog(mockThat, 'Test Title', mockLinkHelper);

      expect(mockThat.dialog.open).toHaveBeenCalledWith(
        expect.anything(),
        expect.objectContaining({
          data: {
            name: 'Test Title',
            dataset: 'test-dataset'
          }
        })
      );
    });

    it('calls onOpen when dialog opens', (done) => {
      mockDialogRef.afterOpened.mockReturnValue(of(null));
      mockDialogRef.afterClosed.mockReturnValue(of(null));

      LinkDialogLauncher.openDialog(mockThat, 'Test', mockLinkHelper);

      setTimeout(() => {
        expect(mockLinkHelper.onOpen).toHaveBeenCalledWith(
          mockThat.service,
          mockDialogRef.componentInstance,
          mockThat.parent.attributes
        );
        done();
      }, 10);
    });

    it('calls onOK when dialog closes with selected items', (done) => {
      const mockResult: LinkDialogData = {
        name: 'Test',
        dataset: 'test-dataset',
        items: [],
        selected: [{ id: '1', name: 'Item 1' }]
      };

      mockDialogRef.afterOpened.mockReturnValue(of(null));
      mockDialogRef.afterClosed.mockReturnValue(of(mockResult));

      LinkDialogLauncher.openDialog(mockThat, 'Test', mockLinkHelper);

      setTimeout(() => {
        expect(mockLinkHelper.onOK).toHaveBeenCalledWith(
          mockResult,
          mockThat.parent.attributes,
          expect.any(Function)
        );
        done();
      }, 10);
    });

    it('calls save callback when onOK is invoked', (done) => {
      const mockResult: LinkDialogData = {
        name: 'Test',
        dataset: 'test-dataset',
        items: [],
        selected: [{ id: '1', name: 'Item 1' }]
      };

      let saveCallback: Function;
      mockLinkHelper.onOK = vi.fn((data, attrs, callback) => {
        saveCallback = callback;
      });

      mockDialogRef.afterOpened.mockReturnValue(of(null));
      mockDialogRef.afterClosed.mockReturnValue(of(mockResult));

      LinkDialogLauncher.openDialog(mockThat, 'Test', mockLinkHelper);

      setTimeout(() => {
        saveCallback();
        expect(mockThat.parent.save).toHaveBeenCalled();
        done();
      }, 10);
    });

    it('ignores close result with no selected items', (done) => {
      const mockResult: LinkDialogData = {
        name: 'Test',
        dataset: 'test-dataset',
        items: [],
        selected: []
      };

      mockDialogRef.afterOpened.mockReturnValue(of(null));
      mockDialogRef.afterClosed.mockReturnValue(of(mockResult));

      LinkDialogLauncher.openDialog(mockThat, 'Test', mockLinkHelper);

      setTimeout(() => {
        expect(mockLinkHelper.onOK).toHaveBeenCalled();
        done();
      }, 10);
    });

    it('handles null dialog result', (done) => {
      mockDialogRef.afterOpened.mockReturnValue(of(null));
      mockDialogRef.afterClosed.mockReturnValue(of(null));

      LinkDialogLauncher.openDialog(mockThat, 'Test', mockLinkHelper);

      setTimeout(() => {
        expect(mockLinkHelper.onOK).not.toHaveBeenCalled();
        done();
      }, 10);
    });

    it('handles result with no selected property', (done) => {
      const mockResult = { name: 'Test', dataset: 'test' };

      mockDialogRef.afterOpened.mockReturnValue(of(null));
      mockDialogRef.afterClosed.mockReturnValue(of(mockResult));

      LinkDialogLauncher.openDialog(mockThat, 'Test', mockLinkHelper);

      setTimeout(() => {
        expect(mockLinkHelper.onOK).not.toHaveBeenCalled();
        done();
      }, 10);
    });

    it('uses correct dataset in dialog parameters', () => {
      mockDialogRef.afterOpened.mockReturnValue(of(null));
      mockDialogRef.afterClosed.mockReturnValue(of(null));

      mockThat.dataset = 'my-custom-dataset';

      LinkDialogLauncher.openDialog(mockThat, 'Test', mockLinkHelper);

      expect(mockThat.dialog.open).toHaveBeenCalledWith(
        expect.anything(),
        expect.objectContaining({
          data: expect.objectContaining({
            dataset: 'my-custom-dataset'
          })
        })
      );
    });

    it('uses correct title in dialog parameters', () => {
      mockDialogRef.afterOpened.mockReturnValue(of(null));
      mockDialogRef.afterClosed.mockReturnValue(of(null));

      LinkDialogLauncher.openDialog(mockThat, 'Source Links', mockLinkHelper);

      expect(mockThat.dialog.open).toHaveBeenCalledWith(
        expect.anything(),
        expect.objectContaining({
          data: expect.objectContaining({
            name: 'Source Links'
          })
        })
      );
    });
  });
});
