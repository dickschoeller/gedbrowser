import { NO_ERRORS_SCHEMA } from '@angular/core';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { MatDialogRef, MatDialogModule, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { ReactiveFormsModule, FormsModule } from '@angular/forms';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import { vi } from 'vitest';

import { NewAttributeDialogComponent } from './new-attribute-dialog.component';

describe('NewAttributeDialogComponent', () => {
  let component: NewAttributeDialogComponent;
  let fixture: ComponentFixture<NewAttributeDialogComponent>;
  let mockDialogRef: any;

  beforeEach(() => {
    mockDialogRef = { close: vi.fn() };

    TestBed.configureTestingModule({
    schemas: [NO_ERRORS_SCHEMA],
    imports: [MatDialogModule, ReactiveFormsModule, FormsModule, MatFormFieldModule, MatInputModule, MatSelectModule, NewAttributeDialogComponent],
    providers: [
      { provide: MatDialogRef, useValue: mockDialogRef },
        {
            provide: MAT_DIALOG_DATA,
            useValue: {
                options: [{ label: 'Test', value: 'test' }],
                default: { type: 'test', text: '', date: '' }
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
});
