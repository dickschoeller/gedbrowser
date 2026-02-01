import { TestBed } from '@angular/core/testing';
import { MatDialogRef, MatDialogModule, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { MatListModule } from '@angular/material/list';
import { RouterTestingModule } from '@angular/router/testing';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { NO_ERRORS_SCHEMA } from '@angular/core';
import { vi } from 'vitest';

/**
 * Configuration for link dialog component tests
 */
export interface LinkDialogTestConfig<TComponent, TData> {
  componentClass: new (...args: any[]) => TComponent;
  createMockData: () => TData;
  dataPropertyName?: string; // 'name' or 'titleString'
  dataPropertyValue?: string; // 'Test Dialog' or 'Link Person'
  hasSelectOne?: boolean; // For LinkPersonDialog
}

/**
 * Setup function for link dialog components
 */
export function setupLinkDialogTest<TData>(mockData: TData) {
  const mockDialogRef = {
    close: vi.fn()
  };

  TestBed.configureTestingModule({
    schemas: [NO_ERRORS_SCHEMA],
    imports: [MatDialogModule, MatListModule, RouterTestingModule, BrowserAnimationsModule],
    providers: [
      { provide: MatDialogRef, useValue: mockDialogRef },
      { provide: MAT_DIALOG_DATA, useValue: mockData }
    ]
  }).compileComponents();

  return { mockDialogRef, mockData };
}

/**
 * Generic test suite for link dialog components
 */
export function describeLinkDialogComponent<TComponent, TData>(
  config: LinkDialogTestConfig<TComponent, TData>,
  describe: (name: string, fn: () => void) => void,
  it: (name: string, fn: () => void) => void,
  expect: any,
  beforeEach: (fn: () => void) => void
) {
  let component: TComponent;
  let mockData: TData;
  let mockDialogRef: any;

  beforeEach(() => {
    mockData = config.createMockData();
    const setup = setupLinkDialogTest(mockData);
    mockDialogRef = setup.mockDialogRef;

    const fixture = TestBed.createComponent(config.componentClass);
    component = fixture.componentInstance;
    (component as any).data = mockData;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should initialize with dialog data', () => {
    const data = (component as any).data;
    expect(data).toBeDefined();
    
    const propName = config.dataPropertyName || 'name';
    const propValue = config.dataPropertyValue || 'Test Dialog';
    expect(data[propName]).toBe(propValue);
  });

  it('should have items in dialog data', () => {
    const data = (component as any).data;
    expect(data.items.length).toBe(3);
  });

  it('should close dialog on onNoClick', () => {
    (component as any).onNoClick();
    expect(mockDialogRef.close).toHaveBeenCalled();
  });

  it('should handle empty selection', () => {
    (component as any).onSelection({}, []);
    expect((component as any).data.selected.length).toBe(0);
  });

  it('should accept titleString input', () => {
    (component as any).titleString = 'Custom Title';
    expect((component as any).titleString).toBe('Custom Title');
  });

  it('should initialize ngOnInit without errors', () => {
    if ((component as any).ngOnInit) {
      expect(() => (component as any).ngOnInit()).not.toThrow();
    }
  });

  it('should initialize ngOnChanges without errors', () => {
    if ((component as any).ngOnChanges) {
      expect(() => (component as any).ngOnChanges()).not.toThrow();
    }
  });

  it('should preserve data reference after selection', () => {
    const originalData = (component as any).data;
    const mockOption = config.hasSelectOne
      ? { value: { id: 1, label: 'Item 1', person: { id: 1 } } }
      : { value: 1, getLabel: () => 'Item 1' };

    (component as any).onSelection({}, [mockOption]);

    expect((component as any).data).toBe(originalData);
  });

  it('should create new array for selected items', () => {
    const oldSelected = (component as any).data.selected;
    const mockOption = config.hasSelectOne
      ? { value: { id: 1, label: 'Item 1', person: { id: 1 } } }
      : { value: 1, getLabel: () => 'Item 1' };

    (component as any).onSelection({}, [mockOption]);

    expect((component as any).data.selected).not.toBe(oldSelected);
  });
}

/**
 * Additional tests specific to LinkDialog (non-person)
 */
export function describeLinkDialogSpecificTests<TComponent>(
  componentFactory: () => TComponent,
  it: (name: string, fn: () => void) => void,
  expect: any
) {
  it('should update selected items on selection change', () => {
    const component = componentFactory();
    const mockOption1 = { value: 1, getLabel: () => 'Item 1' } as any;
    const mockOption2 = { value: 2, getLabel: () => 'Item 2' } as any;

    (component as any).onSelection({}, [mockOption1, mockOption2]);

    expect((component as any).data.selected.length).toBe(2);
    expect((component as any).data.selected[0].id).toBe(1);
    expect((component as any).data.selected[1].id).toBe(2);
  });

  it('should set correct link item properties on selection', () => {
    const component = componentFactory();
    const mockOption = { value: 1, getLabel: () => 'Item 1' } as any;

    (component as any).onSelection({}, [mockOption]);

    const selectedItem = (component as any).data.selected[0];
    expect(selectedItem.id).toBe(1);
    expect(selectedItem.label).toBe('Item 1');
    expect(selectedItem.index).toBe(0);
  });

  it('should handle multiple selections', () => {
    const component = componentFactory();
    const mockOptions = [
      { value: 1, getLabel: () => 'Item 1' },
      { value: 2, getLabel: () => 'Item 2' },
      { value: 3, getLabel: () => 'Item 3' }
    ] as any;

    (component as any).onSelection({}, mockOptions);

    expect((component as any).data.selected.length).toBe(3);
  });
}

/**
 * Additional tests specific to LinkPersonDialog
 */
export function describeLinkPersonDialogSpecificTests<TComponent>(
  componentFactory: () => TComponent,
  it: (name: string, fn: () => void) => void,
  expect: any
) {
  it('should update selected items on selection change', () => {
    const component = componentFactory();
    const mockOption1 = { value: { id: 1, label: 'Person 1', person: { id: 1 } } } as any;
    const mockOption2 = { value: { id: 2, label: 'Person 2', person: { id: 2 } } } as any;

    (component as any).onSelection({}, [mockOption1, mockOption2]);

    expect((component as any).data.selected.length).toBe(2);
    expect((component as any).data.selected[0].id).toBe(1);
    expect((component as any).data.selected[1].id).toBe(2);
  });

  it('should set selectOne to first selected item', () => {
    const component = componentFactory();
    const mockOption = { value: { id: 1, label: 'Person 1', person: { id: 1 } } } as any;

    (component as any).onSelection({}, [mockOption]);

    expect((component as any).data.selectOne).toBeDefined();
    expect((component as any).data.selectOne.id).toBe(1);
  });

  it('should not set selectOne when no items are selected', () => {
    const component = componentFactory();
    (component as any).data.selectOne = undefined;
    (component as any).onSelection({}, []);

    expect((component as any).data.selectOne).toBeUndefined();
  });

  it('should set correct link person item properties on selection', () => {
    const component = componentFactory();
    const mockOption = { value: { id: 1, label: 'Person 1', person: { id: 1, name: 'John' } } } as any;

    (component as any).onSelection({}, [mockOption]);

    const selectedItem = (component as any).data.selected[0];
    expect(selectedItem.id).toBe(1);
    expect(selectedItem.label).toBe('Person 1');
    expect(selectedItem.person).toBeDefined();
  });

  it('should handle multiple selections', () => {
    const component = componentFactory();
    const mockOptions = [
      { value: { id: 1, label: 'Person 1', person: { id: 1 } } },
      { value: { id: 2, label: 'Person 2', person: { id: 2 } } },
      { value: { id: 3, label: 'Person 3', person: { id: 3 } } }
    ] as any;

    (component as any).onSelection({}, mockOptions);

    expect((component as any).data.selected.length).toBe(3);
  });

  it('should initialize selection list on ngOnInit', () => {
    const component = componentFactory();
    (component as any).selectionList = { selectedOptions: undefined };

    (component as any).ngOnInit();

    expect((component as any).selectionList.selectedOptions).toBeDefined();
  });

  it('should update selectOne when multiple persons selected', () => {
    const component = componentFactory();
    const mockOptions = [
      { value: { id: 1, label: 'Person 1', person: { id: 1 } } },
      { value: { id: 2, label: 'Person 2', person: { id: 2 } } }
    ] as any;

    (component as any).onSelection({}, mockOptions);

    expect((component as any).data.selectOne.id).toBe(1);
  });

  it('should initialize selection model with multi flag', () => {
    const component = componentFactory();
    (component as any).data.multi = true;
    (component as any).selectionList = { selectedOptions: undefined };

    (component as any).ngOnInit();

    expect((component as any).selectionList.selectedOptions).toBeDefined();
  });
}
