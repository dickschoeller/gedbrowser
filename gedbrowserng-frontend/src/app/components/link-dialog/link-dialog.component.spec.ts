import { ComponentFixture, TestBed } from '@angular/core/testing';
import { describe, it, expect, beforeEach, vi } from 'vitest';
import { LinkDialogComponent } from './link-dialog.component';
import { LinkDialogData } from '../../models';
import {
  setupLinkDialogTest,
  describeLinkDialogSpecificTests
} from '../testing/dialog-component-spec-helpers';

describe('LinkDialogComponent', () => {
  let component: LinkDialogComponent;
  let fixture: ComponentFixture<LinkDialogComponent>;
  let mockData: LinkDialogData;
  let mockDialogRef: any;

  const createMockData = (): LinkDialogData => ({
    name: 'Test Dialog',
    items: [
      { id: 1, label: 'Item 1' },
      { id: 2, label: 'Item 2' },
      { id: 3, label: 'Item 3' }
    ],
    selected: []
  });

  beforeEach(() => {
    mockData = createMockData();
    const setup = setupLinkDialogTest(mockData);
    mockDialogRef = setup.mockDialogRef;

    fixture = TestBed.createComponent(LinkDialogComponent);
    component = fixture.componentInstance;
    component.data = mockData;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should initialize with dialog data', () => {
    expect(component.data).toBeDefined();
    expect(component.data.name).toBe('Test Dialog');
  });

  it('should have items in dialog data', () => {
    expect(component.data.items.length).toBe(3);
  });

  it('should close dialog on onNoClick', () => {
    component.onNoClick();
    expect(mockDialogRef.close).toHaveBeenCalled();
  });

  it('should handle empty selection', () => {
    component.onSelection({}, []);
    expect(component.data.selected.length).toBe(0);
  });

  it('should accept titleString input', () => {
    component.titleString = 'Custom Title';
    expect(component.titleString).toBe('Custom Title');
  });

  it('should initialize ngOnInit without errors', () => {
    expect(() => component.ngOnInit()).not.toThrow();
  });

  it('should initialize ngOnChanges without errors', () => {
    expect(() => component.ngOnChanges()).not.toThrow();
  });

  it('should preserve data reference after selection', () => {
    const originalData = component.data;
    const mockOption = { value: 1, getLabel: () => 'Item 1' } as any;

    component.onSelection({}, [mockOption]);

    expect(component.data).toBe(originalData);
  });

  it('should create new array for selected items', () => {
    const oldSelected = component.data.selected;
    const mockOption = { value: 1, getLabel: () => 'Item 1' } as any;

    component.onSelection({}, [mockOption]);

    expect(component.data.selected).not.toBe(oldSelected);
  });

  describeLinkDialogSpecificTests(() => component, it, expect);
});
