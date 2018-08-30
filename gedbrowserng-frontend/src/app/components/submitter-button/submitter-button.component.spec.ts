import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SubmitterButtonComponent } from './submitter-button.component';

describe('SubmitterButtonComponent', () => {
  let component: SubmitterButtonComponent;
  let fixture: ComponentFixture<SubmitterButtonComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SubmitterButtonComponent ]
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
