import { NO_ERRORS_SCHEMA } from '@angular/core';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { MatDialogRef, MatDialogModule, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { MatListModule, MatListOption, MatSelectionList } from '@angular/material/list';
import { RouterTestingModule } from '@angular/router/testing';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';
import { SelectionModel } from '@angular/cdk/collections';
import { describe, it, expect, beforeEach, vi } from 'vitest';

import { LinkPersonDialogComponent } from './link-person-dialog.component';
import { LinkPersonDialogData, LinkPersonItem } from '../../models';

describe('LinkPersonDialogComponent', () => {
  let component: LinkPersonDialogComponent;
  let fixture: ComponentFixture<LinkPersonDialogComponent>;
  let mockDialogRef: any;
  let mockData: LinkPersonDialogData;

  beforeEach(() => {
    mockData = {
      titleString: 'Link Person',
      items: [
        { id: 1, label: 'Person 1', person: { id: 1 } },
        { id: 2, label: 'Person 2', person: { id: 2 } },
        { id: 3, label: 'Person 3', person: { id: 3 } }
      ],
      selected: [],
      multi: false
    };

    mockDialogRef = {
      close: vi.fn()
    };

    TestBed.configureTestingModule({
      schemas: [NO_ERRORS_SCHEMA],
      declarations: [ LinkPersonDialogComponent ],
      imports: [ MatDialogModule, MatListModule, RouterTestingModule, NoopAnimationsModule ],
      providers: [
        { provide: MatDialogRef, useValue: mockDialogRef },
        { provide: MAT_DIALOG_DATA, useValue: mockData }
      ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(LinkPersonDialogComponent);
    component = fixture.componentInstance;
    component.data = mockData;
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

  it('should update selected items on selection change', () => {
    const mockOption1 = {
      value: { id: 1, label: 'Person 1', person: { id: 1 } }
    } as any;

    const mockOption2 = {
      value: { id: 2, label: 'Person 2', person: { id: 2 } }
    } as any;

    component.onSelection({}, [mockOption1, mockOption2]);

    expect(component.data.selected.length).toBe(2);
    expect(component.data.selected[0].id).toBe(1);
    expect(component.data.selected[1].id).toBe(2);
  });

  it('should set selectOne to first selected item', () => {
    const mockOption = {
      value: { id: 1, label: 'Person 1', person: { id: 1 } }
    } as any;

    component.onSelection({}, [mockOption]);

    expect(component.data.selectOne).toBeDefined();
    expect(component.data.selectOne.id).toBe(1);
  });

  it('should not set selectOne when no items are selected', () => {
    component.data.selectOne = undefined;
    component.onSelection({}, []);

    expect(component.data.selectOne).toBeUndefined();
  });

  it('should handle empty selection', () => {
    component.onSelection({}, []);
    expect(component.data.selected.length).toBe(0);
  });

  it('should set correct link person item properties on selection', () => {
    const mockOption = {
      value: { id: 1, label: 'Person 1', person: { id: 1, name: 'John' } }
    } as any;

    component.onSelection({}, [mockOption]);

    const selectedItem = component.data.selected[0];
    expect(selectedItem.id).toBe(1);
    expect(selectedItem.label).toBe('Person 1');
    expect(selectedItem.person).toBeDefined();
  });

  it('should handle multiple selections', () => {
    const mockOptions = [
      { value: { id: 1, label: 'Person 1', person: { id: 1 } } },
      { value: { id: 2, label: 'Person 2', person: { id: 2 } } },
      { value: { id: 3, label: 'Person 3', person: { id: 3 } } }
    ] as any;

    component.onSelection({}, mockOptions);

    expect(component.data.selected.length).toBe(3);
  });

  it('should accept titleString input', () => {
    component.titleString = 'Custom Title';
    expect(component.titleString).toBe('Custom Title');
  });

  it('should initialize selection list on ngOnInit', () => {
    component.selectionList = {
      selectedOptions: undefined
    } as any;

    component.ngOnInit();

    expect(component.selectionList.selectedOptions).toBeDefined();
  });

  it('should initialize ngOnChanges without errors', () => {
    expect(() => component.ngOnChanges()).not.toThrow();
  });

  it('should preserve data reference after selection', () => {
    const originalData = component.data;
    const mockOption = {
      value: { id: 1, label: 'Person 1', person: { id: 1 } }
    } as any;

    component.onSelection({}, [mockOption]);

    expect(component.data).toBe(originalData);
  });

  it('should create new array for selected items', () => {
    const oldSelected = component.data.selected;
    const mockOption = {
      value: { id: 1, label: 'Person 1', person: { id: 1 } }
    } as any;

    component.onSelection({}, [mockOption]);

    expect(component.data.selected).not.toBe(oldSelected);
  });

  it('should update selectOne when multiple persons selected', () => {
    const mockOptions = [
      { value: { id: 1, label: 'Person 1', person: { id: 1 } } },
      { value: { id: 2, label: 'Person 2', person: { id: 2 } } }
    ] as any;

    component.onSelection({}, mockOptions);

    expect(component.data.selectOne.id).toBe(1);
  });

  it('should initialize selection model with multi flag', () => {
    component.data.multi = true;
    component.selectionList = { selectedOptions: undefined } as any;

    component.ngOnInit();

    expect(component.selectionList.selectedOptions).toBeDefined();
  });
});
