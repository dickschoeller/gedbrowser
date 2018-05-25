import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SubmitterListPageComponent } from './submitter-list-page.component';

describe('SubmitterListPageComponent', () => {
  let component: SubmitterListPageComponent;
  let fixture: ComponentFixture<SubmitterListPageComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SubmitterListPageComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SubmitterListPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
