import { ComponentFixture, TestBed } from '@angular/core/testing';
import { describe, it, expect, beforeEach, vi } from 'vitest';
import { LinkPersonDialogComponent } from './link-person-dialog.component';
import { LinkPersonDialogData } from '../../models';
import {
  setupLinkDialogTest,
  describeLinkPersonDialogSpecificTests
} from '../testing/dialog-component-spec-helpers';

describe('LinkPersonDialogComponent', () => {
  let component: LinkPersonDialogComponent;
  let fixture: ComponentFixture<LinkPersonDialogComponent>;
  let mockData: LinkPersonDialogData;
  let mockDialogRef: any;

  const createMockData = (): LinkPersonDialogData => ({
    titleString: 'Link Person',
    items: [
      { id: 1, label: 'Person 1', person: { id: 1 } },
      { id: 2, label: 'Person 2', person: { id: 2 } },
      { id: 3, label: 'Person 3', person: { id: 3 } }
    ],
    selected: [],
    multi: false
  });

  beforeEach(() => {
    mockData = createMockData();
    const setup = setupLinkDialogTest(mockData);
    mockDialogRef = setup.mockDialogRef;

    fixture = TestBed.createComponent(LinkPersonDialogComponent);
    component = fixture.componentInstance;
    component.data = mockData;
    // Mock selectionList before detectChanges to avoid ngOnInit error
    component.selectionList = { selectedOptions: undefined } as any;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should initialize with dialog data', () => {
    expect(component.data).toBeDefined();
    expect(component.data.titleString).toBe('Link Person');
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
    const mockOption = { value: { id: 1, label: 'Person 1', person: { id: 1 } } } as any;

    component.onSelection({}, [mockOption]);

    expect(component.data).toBe(originalData);
  });

  it('should create new array for selected items', () => {
    const oldSelected = component.data.selected;
    const mockOption = { value: { id: 1, label: 'Person 1', person: { id: 1 } } } as any;

    component.onSelection({}, [mockOption]);

    expect(component.data.selected).not.toBe(oldSelected);
  });

  describeLinkPersonDialogSpecificTests(() => component, it, expect);
});
