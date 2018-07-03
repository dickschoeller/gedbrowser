import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { NewSubmitterDialogComponent } from './new-submitter-dialog.component';

describe('NewSubmitterDialogComponent', () => {
  let component: NewSubmitterDialogComponent;
  let fixture: ComponentFixture<NewSubmitterDialogComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ NewSubmitterDialogComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(NewSubmitterDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
