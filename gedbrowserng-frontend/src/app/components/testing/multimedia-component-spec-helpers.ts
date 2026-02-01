import { TestBed } from '@angular/core/testing';
import { MatDialogModule } from '@angular/material/dialog';
import { MatMenuModule } from '@angular/material/menu';
import { MatIconModule } from '@angular/material/icon';
import { MatTooltipModule } from '@angular/material/tooltip';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';
import { NO_ERRORS_SCHEMA } from '@angular/core';
import { vi } from 'vitest';
import { of } from 'rxjs';

/**
 * Common test assertions for multimedia button components
 */
export function describeMultimediaButtonCommonTests(
  componentFactory: () => any,
  it: (name: string, fn: () => void) => void,
  expect: any
) {
  it('should create', () => {
    const component = componentFactory();
    expect(component).toBeTruthy();
  });

  it('should accept dataset input', () => {
    const component = componentFactory();
    component.dataset = 'another-dataset';
    expect(component.dataset).toBe('another-dataset');
  });

  it('should accept parent input', () => {
    const component = componentFactory();
    const mockParent = { save: () => {} };
    component.parent = mockParent;
    expect(component.parent).toBe(mockParent);
  });

  it('should have dialog injected', () => {
    const component = componentFactory();
    expect(component.dialog).toBeDefined();
  });
}

/**
 * Test cases specific to MultimediaAddButton
 */
export function describeMultimediaAddButtonTests(
  componentFactory: () => any,
  mockDialogFactory: () => any,
  it: (name: string, fn: () => void) => void,
  expect: any,
  vi: any,
  utils: any
) {
  it('should open multimedia dialog when openMultimediaDialog is called', () => {
    const component = componentFactory();
    const mockDialog = mockDialogFactory();
    component.openMultimediaDialog();
    expect(mockDialog.open).toHaveBeenCalled();
  });

  it('should push new multimedia to parent array', () => {
    const component = componentFactory();
    const mockData = {
      title: 'New Photo',
      files: [{ fileUrl: 'url1' }]
    } as any;

    vi.spyOn(utils.MultimediaDialogHelper, 'buildMultimediaAttribute').mockReturnValue({
      name: 'New Photo',
      value: 'url1'
    } as any);

    const initialLength = component.parent.multimedia.length;
    component.create(mockData);

    expect(component.parent.multimedia.length).toBe(initialLength + 1);
  });

  it('should call parent save after creating multimedia', () => {
    const component = componentFactory();
    const mockData = {
      title: 'Photo',
      files: [{ fileUrl: 'url' }]
    } as any;

    vi.spyOn(utils.MultimediaDialogHelper, 'buildMultimediaAttribute').mockReturnValue({
      name: 'Photo'
    } as any);

    component.create(mockData);

    expect(component.parent.save).toHaveBeenCalled();
  });

  it('should not create multimedia when dialog returns undefined', () => {
    const component = componentFactory();
    const mockDialog = mockDialogFactory();
    mockDialog.open.mockReturnValue({
      afterClosed: () => of(undefined)
    });

    component.openMultimediaDialog();

    expect(component.parent.multimedia.length).toBe(0);
  });

  it('should handle empty multimedia array', () => {
    const component = componentFactory();
    component.parent.multimedia = [];
    expect(component.parent.multimedia.length).toBe(0);
  });

  it('should pass correct data to dialog open', () => {
    const component = componentFactory();
    const mockDialog = mockDialogFactory();
    component.openMultimediaDialog();

    expect(mockDialog.open).toHaveBeenCalledWith(
      expect.anything(),
      expect.objectContaining({
        data: {
          title: 'Title',
          files: [{ fileUrl: '' }]
        }
      })
    );
  });
}

/**
 * Test cases specific to MultimediaEditButton
 */
export function describeMultimediaEditButtonTests(
  componentFactory: () => any,
  mockDialogFactory: () => any,
  it: (name: string, fn: () => void) => void,
  expect: any,
  vi: any,
  utils: any
) {
  it('should accept attributes input', () => {
    const component = componentFactory();
    const mockAttributes = [
      { type: 'multimedia', string: 'Multimedia', tail: '', attributes: [] } as any
    ];
    component.attributes = mockAttributes;
    expect(component.attributes).toBe(mockAttributes);
  });

  it('should accept index input', () => {
    const component = componentFactory();
    component.index = 5;
    expect(component.index).toBe(5);
  });

  it('should open multimedia dialog when edit is called', () => {
    const component = componentFactory();
    const mockDialog = mockDialogFactory();
    component.edit();
    expect(mockDialog.open).toHaveBeenCalled();
  });

  it('should update multimedia attribute when dialog returns data', () => {
    const component = componentFactory();
    const mockData = {
      title: 'Updated Photo',
      files: [{ fileUrl: 'data:image/jpeg;base64,def456' }]
    } as any;

    const builtAttribute = {
      type: 'multimedia',
      string: 'Multimedia',
      tail: '',
      attributes: [
        { type: 'attribute', string: 'Title', tail: 'Updated Photo', attributes: [] },
        { type: 'attribute', string: 'File', tail: 'data:image/jpeg;base64,def456', attributes: [] }
      ]
    } as any;
    vi.spyOn(utils.MultimediaDialogHelper, 'buildMultimediaAttribute').mockReturnValue(builtAttribute);

    component.update(mockData);

    expect(component.attributes[0]).toEqual(builtAttribute);
    expect(component.parent.save).toHaveBeenCalled();
  });

  it('should not update attribute when dialog returns undefined', () => {
    const component = componentFactory();
    const mockDialog = mockDialogFactory();
    mockDialog.open.mockReturnValue({
      afterClosed: () => of(undefined)
    });

    const originalAttribute = { ...component.attributes[0] };
    component.edit();

    expect(component.attributes[0]).toEqual(originalAttribute);
  });

  it('should handle multiple attributes', () => {
    const component = componentFactory();
    component.attributes = [
      { type: 'multimedia', string: 'Multimedia', tail: '', attributes: [] } as any,
      { type: 'multimedia', string: 'Multimedia', tail: '', attributes: [] } as any,
      { type: 'multimedia', string: 'Multimedia', tail: '', attributes: [] } as any
    ];

    expect(component.attributes.length).toBe(3);
  });

  it('should update attribute at correct index', () => {
    const component = componentFactory();
    component.attributes = [
      { type: 'multimedia', string: 'Multimedia', tail: '', attributes: [] } as any,
      { type: 'multimedia', string: 'Multimedia', tail: '', attributes: [] } as any,
      { type: 'multimedia', string: 'Multimedia', tail: '', attributes: [] } as any
    ];
    component.index = 1;

    const updated = {
      type: 'multimedia',
      string: 'Multimedia',
      tail: '',
      attributes: [{ type: 'attribute', string: 'Title', tail: 'Updated Photo', attributes: [] }]
    } as any;
    vi.spyOn(utils.MultimediaDialogHelper, 'buildMultimediaAttribute').mockReturnValue(updated);

    component.update({} as any);

    expect(component.attributes[1]).toEqual(updated);
    expect(component.attributes[0].string).toBe('Multimedia');
    expect(component.attributes[2].string).toBe('Multimedia');
  });

  it('should call parent save after updating', () => {
    const component = componentFactory();
    vi.spyOn(utils.MultimediaDialogHelper, 'buildMultimediaAttribute').mockReturnValue({
      type: 'multimedia',
      string: 'Multimedia',
      tail: '',
      attributes: []
    } as any);

    component.update({} as any);

    expect(component.parent.save).toHaveBeenCalled();
  });

  it('should handle empty attributes array', () => {
    const component = componentFactory();
    component.attributes = [];
    expect(component.attributes.length).toBe(0);
  });

  it('should pass correct data to dialog open', () => {
    const component = componentFactory();
    const mockDialog = mockDialogFactory();
    vi.spyOn(utils.MultimediaDialogHelper, 'buildMultimediaDialogData').mockReturnValue({
      title: 'Edit',
      files: []
    } as any);

    component.edit();

    expect(mockDialog.open).toHaveBeenCalledWith(
      expect.anything(),
      expect.objectContaining({
        data: expect.anything()
      })
    );
  });
}
