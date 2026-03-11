import { NO_ERRORS_SCHEMA } from '@angular/core';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { MatDialogModule, MatDialog } from '@angular/material/dialog';
import { describe, it, expect, beforeEach, vi } from 'vitest';
import { of } from 'rxjs';
import * as utils from '../../utils';
import { MultimediaEditButtonComponent } from './multimedia-edit-button.component';
import { ApiAttribute } from '../../models';
import { describeMultimediaButtonCommonTests, describeMultimediaEditButtonTests } from '../testing/multimedia-component-spec-helpers';

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
    imports: [MatDialogModule, MultimediaEditButtonComponent],
    providers: [
      { provide: MatDialog, useValue: mockDialog }
    ]
}).compileComponents();

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
            type: 'attribute',
            string: 'File',
            tail: 'photo1.jpg',
            attributes: [
              {
                type: 'attribute',
                string: 'Format',
                tail: 'jpg',
                attributes: [
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

  describeMultimediaButtonCommonTests(() => component, it, expect);
  
  describeMultimediaEditButtonTests(
    () => component,
    () => mockDialog,
    it,
    expect,
    vi,
    utils
  );

  it('should call update when dialog returns a result', () => {
    const result = { title: 'Updated', files: [{ fileUrl: 'photo2.jpg' }] } as any;
    mockDialog.open.mockReturnValue({
      afterClosed: () => of(result)
    });
    const updateSpy = vi.spyOn(component, 'update').mockImplementation(() => undefined);

    component.edit();

    expect(updateSpy).toHaveBeenCalledWith(result);
  });

  it('should not call update when dialog returns undefined', () => {
    mockDialog.open.mockReturnValue({ afterClosed: () => of(undefined) });
    const updateSpy = vi.spyOn(component, 'update');

    component.edit();

    expect(updateSpy).not.toHaveBeenCalled();
  });
});
