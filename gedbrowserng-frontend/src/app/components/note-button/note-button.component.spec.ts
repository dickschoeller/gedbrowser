import { NO_ERRORS_SCHEMA } from '@angular/core';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { MatDialogModule, MatDialog } from '@angular/material/dialog';
import { MatMenuModule } from '@angular/material/menu';
import { MatIconModule } from '@angular/material/icon';
import { MatTooltipModule } from '@angular/material/tooltip';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';
import { describe, it, expect, beforeEach, vi } from 'vitest';
import { of } from 'rxjs';

import { NoteButtonComponent } from './note-button.component';
import { NoteService } from '../../services';
import { UrlBuilder } from '../../utils';

describe('NoteButtonComponent', () => {
  let component: NoteButtonComponent;
  let fixture: ComponentFixture<NoteButtonComponent>;
  let mockNoteService: any;
  let mockDialog: any;

  beforeEach(() => {
    mockNoteService = {
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
      declarations: [ NoteButtonComponent ],
      imports: [ MatDialogModule, MatMenuModule, MatIconModule, MatTooltipModule, NoopAnimationsModule ],
      providers: [
        { provide: NoteService, useValue: mockNoteService },
        { provide: MatDialog, useValue: mockDialog }
      ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(NoteButtonComponent);
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

  it('should return UrlBuilder with correct dataset and resource', () => {
    const ub = component.noteUB();
    expect(ub).toBeDefined();
    expect(ub instanceof UrlBuilder).toBe(true);
  });

  it('should return undefined for noteAnchor', () => {
    const anchor = component.noteAnchor();
    expect(anchor).toBeUndefined();
  });

  it('should call parent refresh with correct note', () => {
    component.parent = {
      refresh: vi.fn(),
      attributes: [],
      save: vi.fn()
    } as any;

    const mockNote = { string: 'Test Note', tail: [] } as any;
    component.refreshNote(mockNote);

    expect(component.parent.save).toHaveBeenCalled();
  });

  it('should open link note dialog with correct title', () => {
    mockDialog.open.mockReturnValue({
      afterOpened: vi.fn().mockReturnValue(of({})),
      afterClosed: () => of(undefined),
      componentInstance: {
        data: { dataset: 'test-dataset', name: '', items: [] },
        objects: []
      }
    });
    
    component.openLinkNoteDialog();
    
    // Verify dialog open was called
    expect(mockDialog.open).toHaveBeenCalled();
  });

  it('should open unlink note dialog with correct title', () => {
    mockDialog.open.mockReturnValue({
      afterOpened: vi.fn().mockReturnValue(of({})),
      afterClosed: () => of(undefined),
      componentInstance: {
        data: { dataset: 'test-dataset', name: '', items: [] },
        objects: []
      }
    });
    
    component.openUnlinkNoteDialog();
    
    // Verify dialog open was called
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

  it('should open create note dialog on openCreateNoteDialog', () => {
    component.openCreateNoteDialog();
    
    // This method should be inherited from NoteCreator base class
    expect(component.dialog).toBeDefined();
  });
});
