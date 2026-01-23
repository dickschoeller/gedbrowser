import { waitForAsync, ComponentFixture, TestBed } from '@angular/core/testing';
import { MatDialogModule, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { ReactiveFormsModule, FormsModule } from '@angular/forms';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';

import { NewAttributeDialogComponent } from './new-attribute-dialog.component';

describe('NewAttributeDialogComponent', () => {
  let component: NewAttributeDialogComponent;
  let fixture: ComponentFixture<NewAttributeDialogComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ NewAttributeDialogComponent ],
      imports: [ MatDialogModule, ReactiveFormsModule, FormsModule, MatFormFieldModule, MatInputModule, NoopAnimationsModule ],
      providers: [ { provide: MAT_DIALOG_DATA, useValue: {} } ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(NewAttributeDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
