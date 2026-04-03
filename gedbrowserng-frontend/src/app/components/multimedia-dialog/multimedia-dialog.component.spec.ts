import { NO_ERRORS_SCHEMA } from '@angular/core';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { MatDialogRef, MatDialogModule, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { DragDropModule } from '@angular/cdk/drag-drop';
import { ReactiveFormsModule, FormsModule } from '@angular/forms';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import { MatButtonModule } from '@angular/material/button';
import { provideHttpClient } from '@angular/common/http';
import { provideHttpClientTesting } from '@angular/common/http/testing';
import { vi } from 'vitest';

import { MultimediaDialogComponent } from './multimedia-dialog.component';
import { UserService, AuthApiService, ConfigService } from '../../services';

describe('MultimediaDialogComponent', () => {
  let component: MultimediaDialogComponent;
  let fixture: ComponentFixture<MultimediaDialogComponent>;
  let mockDialogRef: any;
  let mockUserService: any;

  beforeEach(() => {
    mockDialogRef = { close: vi.fn() };
    mockUserService = { currentUser: null };

    TestBed.configureTestingModule({
    schemas: [NO_ERRORS_SCHEMA],
    imports: [MatDialogModule, DragDropModule, ReactiveFormsModule, FormsModule, MatFormFieldModule, MatInputModule, MatSelectModule, MatButtonModule, MultimediaDialogComponent],
    providers: [
      provideHttpClient(),
      provideHttpClientTesting(),
      { provide: MatDialogRef, useValue: mockDialogRef },
        {
            provide: MAT_DIALOG_DATA,
            useValue: {
                title: '',
                description: '',
                citations: [],
                files: []
            }
        },
            { provide: UserService, useValue: mockUserService },
        AuthApiService,
        ConfigService
    ]
})
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(MultimediaDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should initialize formats and source types on ngOnInit', () => {
    component.ngOnInit();
    expect(component.formats.length).toBeGreaterThan(0);
    expect(component.sourceTypes.length).toBeGreaterThan(0);
  });

  it('should close dialog when canceled', () => {
    component.onNoClick();
    expect(mockDialogRef.close).toHaveBeenCalled();
  });

  it('should reorder files on drop', () => {
    component.data.files = [
      { fileUrl: 'a' } as any,
      { fileUrl: 'b' } as any
    ];
    component.drop({ previousIndex: 0, currentIndex: 1 } as any);
    expect(component.data.files[0].fileUrl).toBe('b');
    expect(component.data.files[1].fileUrl).toBe('a');
  });

  it('should report signed-in state from user service', () => {
    mockUserService.currentUser = { id: 'u1' };
    expect(component.hasSignedIn()).toBe(true);
    mockUserService.currentUser = null;
    expect(component.hasSignedIn()).toBe(false);
  });
});
