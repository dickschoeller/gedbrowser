import { NO_ERRORS_SCHEMA } from '@angular/core';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { MatDialogModule, MatDialog } from '@angular/material/dialog';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';
import { describe, it, expect, beforeEach, vi } from 'vitest';
import { of } from 'rxjs';
import * as utils from '../../utils';

import { MultimediaEditButtonComponent } from './multimedia-edit-button.component';
import { ApiAttribute, MultimediaDialogData } from '../../models';

describe('MultimediaEditButtonComponent', () => {
  let component: MultimediaEditButtonComponent;
  let fixture: ComponentFixture<MultimediaEditButtonComponent>;
  let mockDialog: any;

  beforeEach(() => {
    mockDialog = {
      open: vi.fn().mockReturnValue({
        afterClosed: () => of(undefined)
      })
    };

    TestBed.configureTestingModule({
      schemas: [NO_ERRORS_SCHEMA],
      declarations: [ MultimediaEditButtonComponent ],
      imports: [ MatDialogModule, NoopAnimationsModule ],
      providers: [
        { provide: MatDialog, useValue: mockDialog }
      ],
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(MultimediaEditButtonComponent);
    component = fixture.componentInstance;
    component.dataset = 'test-dataset';
    component.parent = {
      save: vi.fn()
    } as any;
    component.attributes = [
      {
        type: 'multimedia',
        string: 'Multimedia',
        tail: '',
        attributes: [
          { type: 'attribute', string: 'Title', tail: 'Photo 1', attributes: [] },
          {
            type: 'attribute', string: 'File', tail: 'photo1.jpg', attributes: [
              {
                type: 'attribute', string: 'Format', tail: 'jpg', attributes: [
                  { type: 'attribute', string: 'Media', tail: 'image', attributes: [] }
                ]
              }
            ]
          }
        ]
      } as ApiAttribute
    ];
    component.index = 0;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should accept dataset input', () => {
    component.dataset = 'another-dataset';
    expect(component.dataset).toBe('another-dataset');
  });

  it('should accept parent input', () => {
    const mockParent = { save: () => {} };
    component.parent = mockParent as any;
    expect(component.parent).toBe(mockParent);
  });

  it('should accept attributes input', () => {
    const mockAttributes = [
      { type: 'multimedia', string: 'Multimedia', tail: '', attributes: [] } as ApiAttribute
    ];
    component.attributes = mockAttributes;
    expect(component.attributes).toBe(mockAttributes);
  });

  it('should accept index input', () => {
    component.index = 5;
    expect(component.index).toBe(5);
  });

  it('should have dialog injected', () => {
    expect(component.dialog).toBeDefined();
  });

  it('should open multimedia dialog when edit is called', () => {
    component.edit();
    expect(mockDialog.open).toHaveBeenCalled();
  });

  it('should update multimedia attribute when dialog returns data', () => {
    const mockData: MultimediaDialogData = {
      title: 'Updated Photo',
      files: [{ fileUrl: 'data:image/jpeg;base64,def456' }] as any
    } as any;

    const builtAttribute: ApiAttribute = {
      type: 'multimedia',
      string: 'Multimedia',
      tail: '',
      attributes: [
        { type: 'attribute', string: 'Title', tail: 'Updated Photo', attributes: [] },
        { type: 'attribute', string: 'File', tail: 'data:image/jpeg;base64,def456', attributes: [] }
      ]
    } as ApiAttribute;
    vi.spyOn(utils.MultimediaDialogHelper, 'buildMultimediaAttribute').mockReturnValue(builtAttribute);

    component.update(mockData);

    expect(component.attributes[0]).toEqual(builtAttribute);
    expect(component.parent.save).toHaveBeenCalled();
  });

  it('should not update attribute when dialog returns undefined', () => {
    mockDialog.open.mockReturnValue({
      afterClosed: () => of(undefined)
    });

    const originalAttribute = { ...component.attributes[0] };
    component.edit();

    expect(component.attributes[0]).toEqual(originalAttribute);
  });

  it('should handle multiple attributes', () => {
    component.attributes = [
      { type: 'multimedia', string: 'Multimedia', tail: '', attributes: [] } as ApiAttribute,
      { type: 'multimedia', string: 'Multimedia', tail: '', attributes: [] } as ApiAttribute,
      { type: 'multimedia', string: 'Multimedia', tail: '', attributes: [] } as ApiAttribute
    ];

    expect(component.attributes.length).toBe(3);
  });

  it('should update attribute at correct index', () => {
    component.attributes = [
      { type: 'multimedia', string: 'Multimedia', tail: '', attributes: [] } as ApiAttribute,
      { type: 'multimedia', string: 'Multimedia', tail: '', attributes: [] } as ApiAttribute,
      { type: 'multimedia', string: 'Multimedia', tail: '', attributes: [] } as ApiAttribute
    ];
    component.index = 1;

    const updated: ApiAttribute = { type: 'multimedia', string: 'Multimedia', tail: '', attributes: [ { type: 'attribute', string: 'Title', tail: 'Updated Photo', attributes: [] } ] } as ApiAttribute;
    vi.spyOn(utils.MultimediaDialogHelper, 'buildMultimediaAttribute').mockReturnValue(updated);

    component.update({} as any);

    expect(component.attributes[1]).toEqual(updated);
    expect(component.attributes[0].string).toBe('Multimedia');
    expect(component.attributes[2].string).toBe('Multimedia');
  });

  it('should call parent save after updating', () => {
    vi.spyOn(utils.MultimediaDialogHelper, 'buildMultimediaAttribute').mockReturnValue({ type: 'multimedia', string: 'Multimedia', tail: '', attributes: [] } as ApiAttribute);

    component.update({} as any);

    expect(component.parent.save).toHaveBeenCalled();
  });

  it('should pass correct data to dialog open', () => {
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

  it('should handle empty attributes array', () => {
    component.attributes = [];
    expect(component.attributes.length).toBe(0);
  });
});
