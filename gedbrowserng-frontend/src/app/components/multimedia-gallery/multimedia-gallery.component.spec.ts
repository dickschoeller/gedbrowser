import { NO_ERRORS_SCHEMA } from '@angular/core';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { MatDialogModule, MatDialog } from '@angular/material/dialog';
import { NgxGalleryModule, NgxGalleryImage } from 'ngx-gallery-15';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { describe, it, expect, beforeEach, vi } from 'vitest';
import { of } from 'rxjs';
import * as utils from '../../utils';

import { MultimediaGalleryComponent } from './multimedia-gallery.component';
import { UserService, AuthApiService, ConfigService } from '../../services';
import { ApiAttribute } from '../../models';

describe('MultimediaGalleryComponent', () => {
  let component: MultimediaGalleryComponent;
  let fixture: ComponentFixture<MultimediaGalleryComponent>;
  let mockUserService: any;
  let mockDialog: any;

  beforeEach(() => {
    mockUserService = {
      currentUser: null
    };

    mockDialog = {
      open: vi.fn().mockReturnValue({
        afterClosed: () => of(undefined)
      })
    };

    TestBed.configureTestingModule({
      schemas: [NO_ERRORS_SCHEMA],
      declarations: [ MultimediaGalleryComponent ],
      imports: [ MatDialogModule, NgxGalleryModule, NoopAnimationsModule, HttpClientTestingModule ],
      providers: [
        { provide: UserService, useValue: mockUserService },
        { provide: MatDialog, useValue: mockDialog },
        AuthApiService,
        ConfigService
      ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(MultimediaGalleryComponent);
    component = fixture.componentInstance;
    component.dataset = 'test-dataset';
    component.parent = { save: vi.fn() };
    component.multimedia = [];
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should initialize gallery options on ngOnInit', () => {
    component.ngOnInit();
    expect(component.galleryOptions).toBeDefined();
    expect(component.galleryOptions.length).toBe(3);
  });

  it('should return empty array for undefined multimedia', () => {
    component.multimedia = undefined;
    const images = component.galleryImages();
    expect(images).toEqual([]);
  });

  it('should return empty array for null multimedia', () => {
    component.multimedia = null;
    const images = component.galleryImages();
    expect(images).toEqual([]);
  });

  it('should return empty array for empty multimedia', () => {
    component.multimedia = [];
    const images = component.galleryImages();
    expect(images).toEqual([]);
  });

  it('should return gallery images for multimedia data', () => {
    const mockMultimedia = [
      { name: 'photo1.jpg', value: 'data:image/jpeg;base64,abc123' } as ApiAttribute,
      { name: 'photo2.jpg', value: 'data:image/jpeg;base64,def456' } as ApiAttribute
    ];
    component.multimedia = mockMultimedia;
    
    vi.spyOn(utils.ImageUtil, 'galleryImages').mockReturnValue([
      { big: 'url1', small: 'url1', medium: 'url1', description: 'photo1.jpg' },
      { big: 'url2', small: 'url2', medium: 'url2', description: 'photo2.jpg' }
    ]);
    
    const images = component.galleryImages();
    expect(images.length).toBeGreaterThan(0);
  });

  it('should return undefined gallery image actions when not signed in', () => {
    mockUserService.currentUser = null;
    const actions = component.galleryImageActions();
    expect(actions).toBeUndefined();
  });

  it('should return gallery image actions when signed in', () => {
    mockUserService.currentUser = { id: 'user123' };
    component.multimedia = [{ name: 'photo.jpg' } as ApiAttribute];
    
    const actions = component.galleryImageActions();
    expect(actions).toBeDefined();
    expect(actions.length).toBe(2);
    expect(actions[0].icon).toContain('pencil');
    expect(actions[1].icon).toContain('trash');
  });

  it('should call buildGalleryOptions with correct screen widths', () => {
    component.ngOnInit();
    
    // Check that we have options for different breakpoints
    const defaultOption = component.galleryOptions[0];
    const mediumOption = component.galleryOptions[1];
    const narrowOption = component.galleryOptions[2];
    
    expect(defaultOption).toBeDefined();
    expect(mediumOption.breakpoint).toBe(500);
    expect(narrowOption.breakpoint).toBe(300);
  });

  it('should set correct gallery options for default width', () => {
    component.ngOnInit();
    const defaultOption = component.galleryOptions[0];
    
    expect(defaultOption.image).toBe(false);
    expect(defaultOption.preview).toBe(true);
    expect(defaultOption.previewCloseOnClick).toBe(true);
    expect(defaultOption.previewFullscreen).toBe(true);
    expect(defaultOption.thumbnailsColumns).toBe(6);
  });

  it('should delete multimedia item at index', () => {
    component.multimedia = [
      { name: 'photo1.jpg' } as ApiAttribute,
      { name: 'photo2.jpg' } as ApiAttribute
    ];
    
    component.deleteButtonClicked(null, 0);
    
    expect(component.multimedia.length).toBe(1);
    expect(component.multimedia[0].name).toBe('photo2.jpg');
    expect(component.parent.save).toHaveBeenCalled();
  });

  it('should open dialog on edit button click', () => {
    component.multimedia = [{ name: 'photo.jpg', attributes: [] } as ApiAttribute];
    mockUserService.currentUser = { id: 'user123' };
    
    const mockDialogRef = {
      afterClosed: () => of(undefined)
    };
    mockDialog.open.mockReturnValue(mockDialogRef);
    
    component.editButtonClicked(null, 0);
    
    expect(mockDialog.open).toHaveBeenCalled();
    expect(component.dialogIndex).toBe(0);
  });

  it('should update multimedia on dialog result', () => {
    const originalMultimedia = { name: 'photo.jpg', value: 'old' } as ApiAttribute;
    component.multimedia = [originalMultimedia];
    component.parent = { save: vi.fn() };
    
    vi.spyOn(utils.MultimediaDialogHelper, 'buildMultimediaAttribute').mockReturnValue({
      name: 'photo.jpg',
      value: 'new'
    } as ApiAttribute);
    
    component.update({ value: 'new' } as any);
    
    expect(component.multimedia[0].value).toBe('new');
    expect(component.parent.save).toHaveBeenCalled();
  });

  it('should call parent save method', () => {
    component.save();
    expect(component.parent.save).toHaveBeenCalled();
  });

  it('should return true for hasSignedIn when user is logged in', () => {
    mockUserService.currentUser = { id: 'user123' };
    expect(component.hasSignedIn()).toBe(true);
  });

  it('should return false for hasSignedIn when user is not logged in', () => {
    mockUserService.currentUser = null;
    expect(component.hasSignedIn()).toBe(false);
  });

  it('should handle multiple multimedia items', () => {
    const multimedia = [
      { name: 'photo1.jpg' } as ApiAttribute,
      { name: 'photo2.jpg' } as ApiAttribute,
      { name: 'photo3.jpg' } as ApiAttribute
    ];
    component.multimedia = multimedia;
    
    const images = component.galleryImages();
    expect(images).toBeDefined();
  });

  it('should properly bind edit button clicked method', () => {
    mockUserService.currentUser = { id: 'user123' };
    const originalMethod = component.editButtonClicked;
    
    const actions = component.galleryImageActions();
    
    // Verify the edit action is bound
    expect(actions[0].onClick).toBeDefined();
  });

  it('should properly bind delete button clicked method', () => {
    mockUserService.currentUser = { id: 'user123' };
    
    const actions = component.galleryImageActions();
    
    // Verify the delete action is bound
    expect(actions[1].onClick).toBeDefined();
  });

  it('should initialize dialogIndex to -1', () => {
    fixture.detectChanges();
    expect(component.dialogIndex).toBe(-1);
  });

  it('should set dialogIndex when edit button clicked', () => {
    component.multimedia = [{ name: 'photo.jpg', attributes: [] } as ApiAttribute];
    mockUserService.currentUser = { id: 'user123' };
    mockDialog.open.mockReturnValue({ afterClosed: () => of(undefined) });
    
    component.editButtonClicked(null, 0);
    
    expect(component.dialogIndex).toBe(0);
  });

  it('should handle gallery options for medium width breakpoint', () => {
    component.ngOnInit();
    const mediumOption = component.galleryOptions[1];
    
    expect(mediumOption.breakpoint).toBe(500);
    expect(mediumOption.width).toBe('300px');
    expect(mediumOption.thumbnailsColumns).toBe(3);
  });

  it('should handle gallery options for narrow width breakpoint', () => {
    component.ngOnInit();
    const narrowOption = component.galleryOptions[2];
    
    expect(narrowOption.breakpoint).toBe(300);
    expect(narrowOption.width).toBe('100%');
    expect(narrowOption.thumbnailsColumns).toBe(2);
  });
});
