import { NO_ERRORS_SCHEMA } from '@angular/core';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { MatDialogModule, MatDialog } from '@angular/material/dialog';
import { MatMenuModule } from '@angular/material/menu';
import { MatIconModule } from '@angular/material/icon';
import { MatTooltipModule } from '@angular/material/tooltip';
import { provideNoopAnimations } from '@angular/platform-browser/animations';
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
        provideNoopAnimations()
    ],
    providers: [{ provide: MatDialog, useValue: mockDialog }]
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
});
