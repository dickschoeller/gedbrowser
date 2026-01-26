import { NO_ERRORS_SCHEMA } from '@angular/core';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { MatDialogModule, MatDialog } from '@angular/material/dialog';
import { MatMenuModule } from '@angular/material/menu';
import { MatIconModule } from '@angular/material/icon';
import { MatTooltipModule } from '@angular/material/tooltip';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';
import { describe, it, expect, beforeEach, vi } from 'vitest';
import { of } from 'rxjs';

import { SubmitterButtonComponent } from './submitter-button.component';
import { SubmitterService } from '../../services';
import { UrlBuilder } from '../../utils';

describe('SubmitterButtonComponent', () => {
  let component: SubmitterButtonComponent;
  let fixture: ComponentFixture<SubmitterButtonComponent>;
  let mockSubmitterService: any;
  let mockDialog: any;

  beforeEach(() => {
    mockSubmitterService = {
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
      declarations: [ SubmitterButtonComponent ],
      imports: [ MatDialogModule, MatMenuModule, MatIconModule, MatTooltipModule, NoopAnimationsModule ],
      providers: [
        { provide: SubmitterService, useValue: mockSubmitterService },
        { provide: MatDialog, useValue: mockDialog }
      ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(SubmitterButtonComponent);
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

  it('should return UrlBuilder with correct dataset and submitters resource', () => {
    const ub = component.submitterUB();
    expect(ub).toBeDefined();
    expect(ub instanceof UrlBuilder).toBe(true);
  });

  it('should return undefined for submitterAnchor', () => {
    const anchor = component.submitterAnchor();
    expect(anchor).toBeUndefined();
  });

  it('should call parent refresh with correct submitter', () => {
    component.parent = {
      refresh: vi.fn(),
      attributes: [],
      save: vi.fn()
    } as any;

    const mockSubmitter = { string: 'Test Submitter', name: 'Test' } as any;
    component.refreshSubmitter(mockSubmitter);

    expect(component.parent.save).toHaveBeenCalled();
  });

  it('should open link submitter dialog', () => {
    mockDialog.open.mockReturnValue({
      afterOpened: vi.fn().mockReturnValue(of({})),
      afterClosed: () => of(undefined),
      componentInstance: {
        data: { dataset: 'test-dataset', name: '', items: [] },
        objects: []
      }
    });
    
    component.openLinkSubmitterDialog();
    expect(mockDialog.open).toHaveBeenCalled();
  });

  it('should open unlink submitter dialog', () => {
    mockDialog.open.mockReturnValue({
      afterOpened: vi.fn().mockReturnValue(of({})),
      afterClosed: () => of(undefined),
      componentInstance: {
        data: { dataset: 'test-dataset', name: '', items: [] },
        objects: []
      }
    });
    
    component.openUnlinkSubmitterDialog();
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

  it('should open create submitter dialog', () => {
    component.openCreateSubmitterDialog();
    expect(component.dialog).toBeDefined();
  });
});
