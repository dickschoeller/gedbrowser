import { NO_ERRORS_SCHEMA } from '@angular/core';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { MatDialogModule, MatDialog } from '@angular/material/dialog';
import { MatMenuModule } from '@angular/material/menu';
import { MatIconModule } from '@angular/material/icon';
import { MatTooltipModule } from '@angular/material/tooltip';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';
import { describe, it, expect, beforeEach, vi } from 'vitest';
import { of } from 'rxjs';
import * as utils from '../../utils';

import { MultimediaAddButtonComponent } from './multimedia-add-button.component';
import { ApiAttribute, MultimediaDialogData } from '../../models';

describe('MultimediaAddButtonComponent', () => {
  let component: MultimediaAddButtonComponent;
  let fixture: ComponentFixture<MultimediaAddButtonComponent>;
  let mockDialog: any;

  beforeEach(() => {
    mockDialog = {
      open: vi.fn().mockReturnValue({
        afterClosed: () => of(undefined)
      })
    };

    TestBed.configureTestingModule({
      schemas: [NO_ERRORS_SCHEMA],
      declarations: [ MultimediaAddButtonComponent ],
      imports: [ MatDialogModule, MatMenuModule, MatIconModule, MatTooltipModule, NoopAnimationsModule ],
      providers: [
        { provide: MatDialog, useValue: mockDialog }
      ],
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(MultimediaAddButtonComponent);
    component = fixture.componentInstance;
    component.dataset = 'test-dataset';
    component.parent = {
      multimedia: [],
      save: vi.fn()
    } as any;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should accept parent input', () => {
    const mockParent = { multimedia: [], save: () => {} };
    component.parent = mockParent as any;
    expect(component.parent).toBe(mockParent);
  });

  it('should accept dataset input', () => {
    component.dataset = 'another-dataset';
    expect(component.dataset).toBe('another-dataset');
  });

  it('should have dialog injected', () => {
    expect(component.dialog).toBeDefined();
  });

  it('should open multimedia dialog when openMultimediaDialog is called', () => {
    component.openMultimediaDialog();
    expect(mockDialog.open).toHaveBeenCalled();
  });

  it('should create multimedia attribute when dialog returns data', () => {
    const mockData: MultimediaDialogData = {
      title: 'Test Photo',
      files: [{ fileUrl: 'data:image/jpeg;base64,abc123' }] as any
    } as any;

    vi.spyOn(utils.MultimediaDialogHelper, 'buildMultimediaAttribute').mockReturnValue({
      name: 'Test Photo',
      value: 'data:image/jpeg;base64,abc123'
    } as ApiAttribute);

    component.create(mockData);

    expect(component.parent.multimedia.length).toBe(1);
    expect(component.parent.save).toHaveBeenCalled();
  });

  it('should not create multimedia when dialog returns undefined', () => {
    mockDialog.open.mockReturnValue({
      afterClosed: () => of(undefined)
    });

    component.openMultimediaDialog();

    expect(component.parent.multimedia.length).toBe(0);
  });

  it('should handle empty multimedia array', () => {
    component.parent.multimedia = [];
    expect(component.parent.multimedia.length).toBe(0);
  });

  it('should push new multimedia to parent array', () => {
    const mockData: MultimediaDialogData = {
      title: 'New Photo',
      files: [{ fileUrl: 'url1' }] as any
    } as any;

    vi.spyOn(utils.MultimediaDialogHelper, 'buildMultimediaAttribute').mockReturnValue({
      name: 'New Photo',
      value: 'url1'
    } as ApiAttribute);

    const initialLength = component.parent.multimedia.length;
    component.create(mockData);

    expect(component.parent.multimedia.length).toBe(initialLength + 1);
  });

  it('should call parent save after creating multimedia', () => {
    const mockData: MultimediaDialogData = {
      title: 'Photo',
      files: [{ fileUrl: 'url' }] as any
    } as any;

    vi.spyOn(utils.MultimediaDialogHelper, 'buildMultimediaAttribute').mockReturnValue({
      name: 'Photo'
    } as ApiAttribute);

    component.create(mockData);

    expect(component.parent.save).toHaveBeenCalled();
  });

  it('should pass correct data to dialog open', () => {
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
});
