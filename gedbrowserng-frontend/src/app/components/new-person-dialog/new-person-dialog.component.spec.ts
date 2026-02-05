import { NO_ERRORS_SCHEMA } from '@angular/core';
import {waitForAsync, ComponentFixture, TestBed} from '@angular/core/testing';
import { MatDialogRef, MatDialogModule, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { ReactiveFormsModule, FormsModule } from '@angular/forms';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import { MatButtonModule } from '@angular/material/button';
import { provideNoopAnimations } from '@angular/platform-browser/animations';

import { NewPersonDialogComponent } from './new-person-dialog.component';

describe('NewPersonDialogComponent', () => {
  let component: NewPersonDialogComponent;
  let fixture: ComponentFixture<NewPersonDialogComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
    schemas: [NO_ERRORS_SCHEMA],
    imports: [MatDialogModule, ReactiveFormsModule, FormsModule, MatFormFieldModule, MatInputModule, MatSelectModule, MatButtonModule, NewPersonDialogComponent],
    providers: [
        provideNoopAnimations()
    ],
    providers: [
        { provide: MatDialogRef, useValue: {} },
        {
            provide: MAT_DIALOG_DATA,
            useValue: {
                name: '',
                sex: ''
            }
        }
    ]
})
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(NewPersonDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
