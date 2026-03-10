import { NO_ERRORS_SCHEMA } from '@angular/core';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { MatDialogModule, MatDialog } from '@angular/material/dialog';
import { MatMenuModule } from '@angular/material/menu';
import { MatIconModule } from '@angular/material/icon';
import { MatTooltipModule } from '@angular/material/tooltip';
import { describe, it, expect, beforeEach, vi } from 'vitest';
import { of } from 'rxjs';
import * as utils from '../../utils';
import { MultimediaAddButtonComponent } from './multimedia-add-button.component';
import { describeMultimediaButtonCommonTests, describeMultimediaAddButtonTests } from '../testing/multimedia-component-spec-helpers';

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
    imports: [MatDialogModule, MatMenuModule, MatIconModule, MatTooltipModule, MultimediaAddButtonComponent],
    providers: [
      { provide: MatDialog, useValue: mockDialog }
    ]
}).compileComponents();

    fixture = TestBed.createComponent(MultimediaAddButtonComponent);
    component = fixture.componentInstance;
    component.dataset = 'test-dataset';
    component.parent = {
      multimedia: [],
      save: vi.fn()
    } as any;
    fixture.detectChanges();
  });

  describeMultimediaButtonCommonTests(() => component, it, expect);
  
  describeMultimediaAddButtonTests(
    () => component,
    () => mockDialog,
    it,
    expect,
    vi,
    utils
  );

  it('should create multimedia attribute when dialog returns data', () => {
    const mockData = {
      title: 'Test Photo',
      files: [{ fileUrl: 'data:image/jpeg;base64,abc123' }]
    } as any;

    vi.spyOn(utils.MultimediaDialogHelper, 'buildMultimediaAttribute').mockReturnValue({
      name: 'Test Photo',
      value: 'data:image/jpeg;base64,abc123'
    } as any);

    component.create(mockData);

    expect(component.parent.multimedia.length).toBe(1);
    expect(component.parent.save).toHaveBeenCalled();
  });

  it('should call create when dialog returns result', () => {
    const result = { title: 'From dialog', files: [{ fileUrl: 'x' }] } as any;
    mockDialog.open.mockReturnValue({ afterClosed: () => of(result) });
    const createSpy = vi.spyOn(component, 'create').mockImplementation(() => undefined);

    component.openMultimediaDialog();

    expect(createSpy).toHaveBeenCalledWith(result);
  });

  it('should initialize multimedia array when parent multimedia is undefined', () => {
    component.parent = { save: vi.fn() } as any;
    vi.spyOn(utils.MultimediaDialogHelper, 'buildMultimediaAttribute').mockReturnValue({} as any);

    component.create({ title: 'x', files: [{ fileUrl: 'x' }] } as any);

    expect(component.parent.multimedia).toBeDefined();
    expect(component.parent.multimedia.length).toBe(1);
  });

  it('should call optional refreshMultimedia callback', () => {
    const refreshMultimedia = vi.fn();
    component.parent = {
      multimedia: [],
      save: vi.fn(),
      refreshMultimedia,
    } as any;
    vi.spyOn(utils.MultimediaDialogHelper, 'buildMultimediaAttribute').mockReturnValue({} as any);

    component.create({ title: 'x', files: [{ fileUrl: 'x' }] } as any);

    expect(refreshMultimedia).toHaveBeenCalled();
  });
});
