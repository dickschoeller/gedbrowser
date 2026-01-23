import {waitForAsync, ComponentFixture, TestBed} from '@angular/core/testing';
import { MatDialogModule, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { ReactiveFormsModule, FormsModule } from '@angular/forms';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';

import { NewPersonDialogComponent } from './new-person-dialog.component';

describe('NewPersonDialogComponent', () => {
  let component: NewPersonDialogComponent;
  let fixture: ComponentFixture<NewPersonDialogComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ NewPersonDialogComponent ],
      imports: [ MatDialogModule, ReactiveFormsModule, FormsModule, MatFormFieldModule, MatInputModule, NoopAnimationsModule ],
      providers: [ { provide: MAT_DIALOG_DATA, useValue: {} } ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(NewPersonDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
