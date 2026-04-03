import { NO_ERRORS_SCHEMA } from '@angular/core';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { MatDialogModule, MatDialog } from '@angular/material/dialog';
import { provideHttpClient } from '@angular/common/http';
import { provideHttpClientTesting } from '@angular/common/http/testing';
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
      imports: [MatDialogModule, MultimediaGalleryComponent],
      providers: [
        provideHttpClient(),
        provideHttpClientTesting(),
        { provide: UserService, useValue: mockUserService },
        { provide: MatDialog, useValue: mockDialog },
        AuthApiService,
        ConfigService
      ]
    }).compileComponents();
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

  it('should initialize lightgallery settings', () => {
    component.ngOnInit();
    expect(component.lightGallerySettings).toBeDefined();
    expect(component.lightGallerySettings.thumbnail).toBe(true);
    expect(component.lightGallerySettings.download).toBe(false);
  });

  it('should return empty array for undefined multimedia', () => {
    component.multimedia = undefined as any;
    const images = component.galleryImages();
    expect(images).toEqual([]);
  });

  it('should return empty array for null multimedia', () => {
    component.multimedia = null as any;
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
      { big: 'url1', small: 'url1', medium: 'url1', description: 'photo1.jpg', url: 'url1' },
      { big: 'url2', small: 'url2', medium: 'url2', description: 'photo2.jpg', url: 'url2' }
    ]);

    const images = component.galleryImages();
    expect(images.length).toBeGreaterThan(0);
  });

  it('should delete multimedia item at index', () => {
    component.multimedia = [
      { name: 'photo1.jpg' } as ApiAttribute,
      { name: 'photo2.jpg' } as ApiAttribute
    ];

    component.deleteButtonClicked(new Event('click'), 0);

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

    component.editButtonClicked(new Event('click'), 0);

    expect(mockDialog.open).toHaveBeenCalled();
    expect(component.dialogIndex).toBe(0);
  });

  it('should update multimedia on dialog result', () => {
    const originalMultimedia = { name: 'photo.jpg', value: 'old' } as ApiAttribute;
    component.multimedia = [originalMultimedia];
    component.parent = { save: vi.fn() };
    component.dialogIndex = 0;

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

  it('should update selected image index on slide event', () => {
    component.onBeforeSlide({ index: 2 } as any);
    expect(component.selectedImageIndex).toBe(2);
  });

  it('should initialize lightGallery instance from onGalleryInit', () => {
    const lightGallery = { refresh: vi.fn() } as any;
    component.onGalleryInit({ instance: lightGallery } as any);

    (component as any).needGalleryRefresh = true;
    component.ngAfterViewChecked();

    expect(lightGallery.refresh).toHaveBeenCalled();
    expect((component as any).needGalleryRefresh).toBe(false);
  });

  it('should refresh on multimedia input change', () => {
    const refreshSpy = vi.spyOn(component as any, 'refreshGalleryImages');
    component.ngOnChanges({ multimedia: { currentValue: [], previousValue: null, firstChange: false, isFirstChange: () => false } as any });
    expect(refreshSpy).toHaveBeenCalled();
  });

  it('should not refresh on unrelated input change', () => {
    const refreshSpy = vi.spyOn(component as any, 'refreshGalleryImages');
    component.ngOnChanges({ dataset: { currentValue: 'a', previousValue: 'b', firstChange: false, isFirstChange: () => false } as any });
    expect(refreshSpy).not.toHaveBeenCalled();
  });

  it('should refresh multimedia when refreshMultimedia is called', () => {
    const forceViewRefreshSpy = vi.spyOn(component as any, 'forceViewRefresh');
    component.refreshMultimedia();
    expect(forceViewRefreshSpy).toHaveBeenCalled();
  });

  it('should update multimedia when edit dialog returns data', () => {
    component.multimedia = [{ name: 'photo.jpg', attributes: [] } as ApiAttribute];
    const event = new Event('click');
    const preventSpy = vi.spyOn(event, 'preventDefault');
    const stopSpy = vi.spyOn(event, 'stopPropagation');
    const result = { title: 'Updated', files: [{ fileUrl: 'new.jpg' }] } as any;

    mockDialog.open.mockReturnValue({ afterClosed: () => of(result) });
    const updateSpy = vi.spyOn(component, 'update').mockImplementation(() => undefined);

    component.editButtonClicked(event, 0);

    expect(preventSpy).toHaveBeenCalled();
    expect(stopSpy).toHaveBeenCalled();
    expect(updateSpy).toHaveBeenCalledWith(result);
  });

  it('should handle errors while building gallery images', () => {
    component.multimedia = [{ name: 'broken' } as ApiAttribute];
    vi.spyOn(utils.ImageUtil, 'galleryImages').mockImplementation(() => {
      throw new Error('bad data');
    });

    const images = component.galleryImages();

    expect(images).toEqual([]);
    expect(component.selectedImageIndex).toBe(0);
  });

  it('should escape HTML special characters in description', () => {
    expect(component.escapeHtml('<script>alert("xss")</script>')).toBe('&lt;script&gt;alert(&quot;xss&quot;)&lt;/script&gt;');
  });

  it('should escape ampersands in description', () => {
    expect(component.escapeHtml('Tom & Jerry')).toBe('Tom &amp; Jerry');
  });

  it('should return empty string for undefined description', () => {
    expect(component.escapeHtml(undefined)).toBe('');
  });

  it('should return empty string for null description', () => {
    expect(component.escapeHtml(null)).toBe('');
  });
});
