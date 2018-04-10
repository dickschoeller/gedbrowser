import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SubmitterListComponent } from './submitter-list.component';

describe('SubmitterListComponent', () => {
  let component: SubmitterListComponent;
  let fixture: ComponentFixture<SubmitterListComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SubmitterListComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SubmitterListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
