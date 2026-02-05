import { NO_ERRORS_SCHEMA } from '@angular/core';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { MatDialogRef, MatDialogModule, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { ReactiveFormsModule, FormsModule } from '@angular/forms';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { provideNoopAnimations } from '@angular/platform-browser/animations';

import { NewSubmitterDialogComponent } from './new-submitter-dialog.component';

describe('NewSubmitterDialogComponent', () => {
  let component: NewSubmitterDialogComponent;
  let fixture: ComponentFixture<NewSubmitterDialogComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
    schemas: [NO_ERRORS_SCHEMA],
    imports: [MatDialogModule, ReactiveFormsModule, FormsModule, MatFormFieldModule, MatInputModule, NewSubmitterDialogComponent],
    providers: [
        provideNoopAnimations()
    ],
    providers: [{ provide: MatDialogRef, useValue: {} }, { provide: MAT_DIALOG_DATA, useValue: {} }]
})
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(NewSubmitterDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
