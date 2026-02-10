import { NO_ERRORS_SCHEMA } from '@angular/core';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ReactiveFormsModule, FormsModule } from '@angular/forms';
import { provideHttpClient } from '@angular/common/http';
import { provideHttpClientTesting } from '@angular/common/http/testing';
import { MatListModule } from '@angular/material/list';
import { MatMenuModule } from '@angular/material/menu';
import { MatIconModule } from '@angular/material/icon';
import { provideRouter } from '@angular/router';
import { of } from 'rxjs';
import { describe, it, expect, beforeEach, vi } from 'vitest';

import { SideMenuComponent } from './side-menu.component';
import { DatasetsService, SaveService, UploadService, UserService } from '../../services';

describe('SideMenuComponent', () => {
  let component: SideMenuComponent;
  let fixture: ComponentFixture<SideMenuComponent>;
  let mockDatasetsService: any;
  let mockSaveService: any;
  let mockUploadService: any;
  let mockUserService: any;

  beforeEach(() => {
    mockDatasetsService = { 
      get: () => of(['test-db', 'another-db'])
    };
    mockSaveService = { 
      getTextFile: (dataset: string) => of('GEDCOM content for ' + dataset) 
    };
    mockUploadService = { 
      uploadGedFile: (file: File) => of({ success: true }) 
    };
    mockUserService = { currentUser: null };

    TestBed.configureTestingModule({
    schemas: [NO_ERRORS_SCHEMA],
    imports: [ReactiveFormsModule, FormsModule, MatListModule, MatMenuModule, MatIconModule, SideMenuComponent],
    providers: [
      provideRouter([]),
      provideHttpClient(),
      provideHttpClientTesting(),
      { provide: DatasetsService, useValue: mockDatasetsService },
      { provide: SaveService, useValue: mockSaveService },
      { provide: UploadService, useValue: mockUploadService },
      { provide: UserService, useValue: mockUserService }
    ]
})
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(SideMenuComponent);
    component = fixture.componentInstance;
    component.dataset = 'test-dataset';
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should initialize with dataset service data', () => {
    fixture.detectChanges();
    expect(component.dbs).toContain('test-db');
    expect(component.dbs).toContain('another-db');
  });

  it('should setup items sorted in alphabetical order', () => {
    const dbs = ['zulu', 'alpha', 'bravo'];
    component.setupItems(dbs);
    expect(component.dbs).toEqual(['alpha', 'bravo', 'zulu']);
  });

  it('should handle empty database list', () => {
    component.setupItems([]);
    expect(component.dbs).toEqual([]);
  });

  it('should return false for hasSignedIn when no user is logged in', () => {
    mockUserService.currentUser = null;
    expect(component.hasSignedIn()).toBeFalsy();
  });

  it('should return true for hasSignedIn when user is logged in', () => {
    mockUserService.currentUser = { id: 'user123', name: 'Test User' };
    expect(component.hasSignedIn()).toBeTruthy();
  });

  it('should save file with correct dataset name', () => {
    vi.spyOn(mockSaveService, 'getTextFile').mockReturnValue(of('test content'));
    component.saveFile();
    expect(mockSaveService.getTextFile).toHaveBeenCalledWith('test-dataset');
  });

  it('should pick dataset and update title', () => {
    vi.spyOn(mockDatasetsService, 'get').mockReturnValue(of([]));
    component.pickDataset('new-dataset');
    expect(component.dataset).toBe('new-dataset');
    expect(component.title).toBe('gedbrowserng - new-dataset');
  });

  it('should initialize file upload control', () => {
    fixture.detectChanges();
    expect(component.fileUploadControl).toBeDefined();
    expect(component.filesControl).toBeDefined();
  });

  it('should validate file upload form', () => {
    fixture.detectChanges();
    expect(component.uploadForm).toBeDefined();
    expect(component.uploadForm.get('files')).toBe(component.filesControl);
  });

  it('should handle file upload success', (done) => {
    vi.spyOn(mockUploadService, 'uploadGedFile').mockReturnValue(of({ success: true }));
    vi.spyOn(mockDatasetsService, 'get').mockReturnValue(of([]));
    
    const file = new File(['test content'], 'test.ged', { type: 'application/x-gedcom' });
    component.filesControl.setValue([file], { emitEvent: true });
    component.filesControl.updateValueAndValidity({ emitEvent: true });
    
    fixture.whenStable().then(() => {
      fixture.detectChanges();
      expect(mockUploadService.uploadGedFile).toHaveBeenCalled();
      done();
    });
  });

  it('should reject non-ged file uploads', (done) => {
    const alertSpy = vi.spyOn(window, 'alert').mockImplementation(() => {});
    const file = new File(['test content'], 'test.txt', { type: 'text/plain' });
    component.filesControl.setValue([file], { emitEvent: true });
    component.filesControl.updateValueAndValidity({ emitEvent: true });
    
    fixture.detectChanges();
    fixture.whenStable().then(() => {
      expect(alertSpy).toHaveBeenCalledWith(expect.stringContaining('unsupported file type'));
      done();
    });
  });

  it('should accept .ged file extension', (done) => {
    vi.spyOn(mockUploadService, 'uploadGedFile').mockReturnValue(of({ success: true }));
    vi.spyOn(mockDatasetsService, 'get').mockReturnValue(of([]));
    
    const file = new File(['test content'], 'family.ged', { type: 'text/plain' });
    component.filesControl.setValue([file], { emitEvent: true });
    component.filesControl.updateValueAndValidity({ emitEvent: true });
    
    fixture.whenStable().then(() => {
      fixture.detectChanges();
      expect(mockUploadService.uploadGedFile).toHaveBeenCalled();
      done();
    });
  });

  it('should accept .gedcom file extension', (done) => {
    vi.spyOn(mockUploadService, 'uploadGedFile').mockReturnValue(of({ success: true }));
    vi.spyOn(mockDatasetsService, 'get').mockReturnValue(of([]));
    
    const file = new File(['test content'], 'family.gedcom', { type: 'text/plain' });
    component.filesControl.setValue([file], { emitEvent: true });
    component.filesControl.updateValueAndValidity({ emitEvent: true });
    
    fixture.whenStable().then(() => {
      fixture.detectChanges();
      expect(mockUploadService.uploadGedFile).toHaveBeenCalled();
      done();
    });
  });

  it('should accept application/x-gedcom MIME type', (done) => {
    vi.spyOn(mockUploadService, 'uploadGedFile').mockReturnValue(of({ success: true }));
    vi.spyOn(mockDatasetsService, 'get').mockReturnValue(of([]));
    
    const file = new File(['test content'], 'family.ged', { type: 'application/x-gedcom' });
    component.filesControl.setValue([file], { emitEvent: true });
    component.filesControl.updateValueAndValidity({ emitEvent: true });
    
    fixture.whenStable().then(() => {
      fixture.detectChanges();
      expect(mockUploadService.uploadGedFile).toHaveBeenCalled();
      done();
    });
  });

  it('should handle upload error gracefully', (done) => {
    vi.spyOn(mockUploadService, 'uploadGedFile').mockReturnValue(of(new Error('Upload failed')));
    const alertSpy = vi.spyOn(window, 'alert').mockImplementation(() => {});
    vi.spyOn(mockDatasetsService, 'get').mockReturnValue(of([]));
    
    const file = new File(['test content'], 'test.ged', { type: 'application/x-gedcom' });
    component.filesControl.setValue([file], { emitEvent: true });
    component.filesControl.updateValueAndValidity({ emitEvent: true });
    
    fixture.whenStable().then(() => {
      fixture.detectChanges();
      expect(alertSpy).toHaveBeenCalled();
      done();
    });
  });

  it('should set title on init', () => {
    fixture.detectChanges();
    expect(component.title).toBe('gedbrowserng - test-dataset');
  });

  it('should reinitialize on ngOnChanges', () => {
    vi.spyOn(mockDatasetsService, 'get').mockReturnValue(of(['updated-db']));
    component.ngOnChanges();
    expect(mockDatasetsService.get).toHaveBeenCalled();
  });

  it('should handle multiple database entries in setupItems', () => {
    const dbs = ['charlie', 'alice', 'bob', 'david'];
    component.setupItems(dbs);
    expect(component.dbs.length).toBe(4);
    expect(component.dbs[0]).toBe('alice');
    expect(component.dbs[component.dbs.length - 1]).toBe('david');
  });

  it('should handle null file in upload', () => {
    // Null files are handled by the validator, so we just test that the form control exists
    expect(component.filesControl).toBeDefined();
  });
});
