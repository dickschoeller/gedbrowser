import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { NewSourceDialogComponent } from './new-source-dialog.component';

describe('NewSourceDialogComponent', () => {
  let component: NewSourceDialogComponent;
  let fixture: ComponentFixture<NewSourceDialogComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ NewSourceDialogComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(NewSourceDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
