import { TestBed } from '@angular/core/testing';
import { MatDialogRef, MatDialogModule, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { MatListModule } from '@angular/material/list';
import { RouterTestingModule } from '@angular/router/testing';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { NO_ERRORS_SCHEMA } from '@angular/core';
import { vi } from 'vitest';



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
