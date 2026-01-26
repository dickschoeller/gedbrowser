import { NO_ERRORS_SCHEMA } from '@angular/core';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { MatDialogModule, MatDialog } from '@angular/material/dialog';
import { MatMenuModule } from '@angular/material/menu';
import { MatIconModule } from '@angular/material/icon';
import { MatTooltipModule } from '@angular/material/tooltip';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';
import { describe, it, expect, beforeEach, vi } from 'vitest';
import { of } from 'rxjs';

import { SourceButtonComponent } from './source-button.component';
import { SourceService } from '../../services';
import { UrlBuilder } from '../../utils';

describe('SourceButtonComponent', () => {
  let component: SourceButtonComponent;
  let fixture: ComponentFixture<SourceButtonComponent>;
  let mockSourceService: any;
  let mockDialog: any;

  beforeEach(() => {
    mockSourceService = {
      getUrls: () => ({}),
      getAll: vi.fn().mockReturnValue(of([]))
    };

    mockDialog = {
      open: vi.fn().mockReturnValue({
        afterOpened: vi.fn().mockReturnValue(of({})),
        afterClosed: () => of(undefined),
        componentInstance: {
          data: { dataset: 'test-dataset', name: '', items: [] },
          objects: []
        }
      })
    };

    TestBed.configureTestingModule({
      schemas: [NO_ERRORS_SCHEMA],
      declarations: [ SourceButtonComponent ],
      imports: [ MatDialogModule, MatMenuModule, MatIconModule, MatTooltipModule, NoopAnimationsModule ],
      providers: [
        { provide: SourceService, useValue: mockSourceService },
        { provide: MatDialog, useValue: mockDialog }
      ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(SourceButtonComponent);
    component = fixture.componentInstance;
    component.dataset = 'test-dataset';
    component.parent = {
      attributes: [],
      refresh: vi.fn(),
      save: vi.fn()
    } as any;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should return UrlBuilder with correct dataset and sources resource', () => {
    const ub = component.sourceUB();
    expect(ub).toBeDefined();
    expect(ub instanceof UrlBuilder).toBe(true);
  });

  it('should return undefined for sourceAnchor', () => {
    const anchor = component.sourceAnchor();
    expect(anchor).toBeUndefined();
  });

  it('should call parent refresh with correct source', () => {
    component.parent = {
      refresh: vi.fn(),
      attributes: [],
      save: vi.fn()
    } as any;

    const mockSource = { string: 'Test Source', title: 'Test' } as any;
    component.refreshSource(mockSource);

    expect(component.parent.save).toHaveBeenCalled();
  });

  it('should open link source dialog', () => {
    mockDialog.open.mockReturnValue({
      afterOpened: vi.fn().mockReturnValue(of({})),
      afterClosed: () => of(undefined),
      componentInstance: {
        data: { dataset: 'test-dataset', name: '', items: [] },
        objects: []
      }
    });
    
    component.openLinkSourceDialog();
    expect(mockDialog.open).toHaveBeenCalled();
  });

  it('should open unlink source dialog', () => {
    mockDialog.open.mockReturnValue({
      afterOpened: vi.fn().mockReturnValue(of({})),
      afterClosed: () => of(undefined),
      componentInstance: {
        data: { dataset: 'test-dataset', name: '', items: [] },
        objects: []
      }
    });
    
    component.openUnlinkSourceDialog();
    expect(mockDialog.open).toHaveBeenCalled();
  });

  it('should have dialog property from parent class', () => {
    expect(component.dialog).toBeDefined();
  });

  it('should have service property from parent class', () => {
    expect(component.service).toBeDefined();
  });

  it('should accept parent input', () => {
    const mockParent = { attributes: [], refresh: () => {} };
    component.parent = mockParent as any;
    expect(component.parent).toBe(mockParent);
  });

  it('should accept dataset input', () => {
    component.dataset = 'another-dataset';
    expect(component.dataset).toBe('another-dataset');
  });

  it('should open create source dialog', () => {
    component.openCreateSourceDialog();
    expect(component.dialog).toBeDefined();
  });
});
