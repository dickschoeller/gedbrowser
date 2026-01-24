import { NO_ERRORS_SCHEMA } from '@angular/core';
import { waitForAsync, ComponentFixture, TestBed } from '@angular/core/testing';
import { MatDialogModule } from '@angular/material/dialog';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';

import { SubmitterButtonComponent } from './submitter-button.component';
import { SubmitterService } from '../../services';

describe('SubmitterButtonComponent', () => {
  let component: SubmitterButtonComponent;
  let fixture: ComponentFixture<SubmitterButtonComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      schemas: [NO_ERRORS_SCHEMA],
      declarations: [ SubmitterButtonComponent ],
      imports: [ MatDialogModule, NoopAnimationsModule ],
      providers: [ SubmitterService ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SubmitterButtonComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
