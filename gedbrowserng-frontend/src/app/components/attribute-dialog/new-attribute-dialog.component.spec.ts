import { NO_ERRORS_SCHEMA } from '@angular/core';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { MatDialog, MatDialogRef, MatDialogModule, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { ReactiveFormsModule, FormsModule } from '@angular/forms';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import { vi } from 'vitest';
import { of } from 'rxjs';

import { NewAttributeDialogComponent } from './new-attribute-dialog.component';

describe('NewAttributeDialogComponent', () => {
  let component: NewAttributeDialogComponent;
  let fixture: ComponentFixture<NewAttributeDialogComponent>;
  let mockDialogRef: any;
  let mockDialog: any;

  beforeEach(() => {
    mockDialogRef = { close: vi.fn() };
    mockDialog = {
      open: vi.fn().mockReturnValue({ afterClosed: () => of(undefined) })
    };

    TestBed.configureTestingModule({
    schemas: [NO_ERRORS_SCHEMA],
    imports: [MatDialogModule, ReactiveFormsModule, FormsModule, MatFormFieldModule, MatInputModule, MatSelectModule, NewAttributeDialogComponent],
    providers: [
      { provide: MatDialogRef, useValue: mockDialogRef },
      { provide: MatDialog, useValue: mockDialog },
        {
            provide: MAT_DIALOG_DATA,
            useValue: {
                options: [{ label: 'Test', value: 'test' }],
                default: { type: 'test', text: '', date: '' },
                canDelete: false
            }
        }
    ]
})
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(NewAttributeDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should close dialog on cancel', () => {
    component.onNoClick();
    expect(mockDialogRef.close).toHaveBeenCalled();
  });

  it('should open confirm dialog on delete click', () => {
    component.onDeleteClick();
    expect(mockDialog.open).toHaveBeenCalled();
  });

  it('should close editor dialog with deleted flag when deletion confirmed', () => {
    mockDialog.open.mockReturnValue({ afterClosed: () => of(true) });
    component.onDeleteClick();
    expect(mockDialogRef.close).toHaveBeenCalledWith(
      expect.objectContaining({ deleted: true })
    );
  });

  it('should not close editor dialog when deletion cancelled', () => {
    mockDialog.open.mockReturnValue({ afterClosed: () => of(false) });
    component.onDeleteClick();
    expect(mockDialogRef.close).not.toHaveBeenCalled();
  });
});
