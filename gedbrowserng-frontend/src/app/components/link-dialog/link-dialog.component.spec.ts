import { NO_ERRORS_SCHEMA } from '@angular/core';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { MatDialogRef, MatDialogModule, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { MatListModule, MatListOption } from '@angular/material/list';
import { RouterTestingModule } from '@angular/router/testing';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { describe, it, expect, beforeEach, vi } from 'vitest';

import { LinkDialogComponent } from './link-dialog.component';
import { LinkDialogData, LinkItem } from '../../models';

describe('LinkDialogComponent', () => {
  let component: LinkDialogComponent;
  let fixture: ComponentFixture<LinkDialogComponent>;
  let mockDialogRef: any;
  let mockData: LinkDialogData;

  beforeEach(() => {
    mockData = {
      name: 'Test Dialog',
      items: [
        { id: 1, label: 'Item 1' },
        { id: 2, label: 'Item 2' },
        { id: 3, label: 'Item 3' }
      ],
      selected: []
    };

    mockDialogRef = {
      close: vi.fn()
    };

    TestBed.configureTestingModule({
      schemas: [NO_ERRORS_SCHEMA],
      declarations: [ LinkDialogComponent ],
      imports: [ MatDialogModule, MatListModule, RouterTestingModule, BrowserAnimationsModule ],
      providers: [
        { provide: MatDialogRef, useValue: mockDialogRef },
        { provide: MAT_DIALOG_DATA, useValue: mockData }
      ]
    })
    .compileComponents();
  });

  beforeEach(() => {
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

  it('should update selected items on selection change', () => {
    const mockOption1 = {
      value: 1,
      getLabel: () => 'Item 1'
    } as any;

    const mockOption2 = {
      value: 2,
      getLabel: () => 'Item 2'
    } as any;

    component.onSelection({}, [mockOption1, mockOption2]);

    expect(component.data.selected.length).toBe(2);
    expect(component.data.selected[0].id).toBe(1);
    expect(component.data.selected[1].id).toBe(2);
  });

  it('should handle empty selection', () => {
    component.onSelection({}, []);
    expect(component.data.selected.length).toBe(0);
  });

  it('should set correct link item properties on selection', () => {
    const mockOption = {
      value: 1,
      getLabel: () => 'Item 1'
    } as any;

    component.onSelection({}, [mockOption]);

    const selectedItem = component.data.selected[0];
    expect(selectedItem.id).toBe(1);
    expect(selectedItem.label).toBe('Item 1');
    expect(selectedItem.index).toBe(0);
  });

  it('should handle multiple selections', () => {
    const mockOptions = [
      { value: 1, getLabel: () => 'Item 1' },
      { value: 2, getLabel: () => 'Item 2' },
      { value: 3, getLabel: () => 'Item 3' }
    ] as any;

    component.onSelection({}, mockOptions);

    expect(component.data.selected.length).toBe(3);
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
});
