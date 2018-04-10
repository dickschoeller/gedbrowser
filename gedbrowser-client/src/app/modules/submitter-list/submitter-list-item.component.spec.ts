import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SubmitterListItemComponent } from './submitter-list-item.component';

describe('SubmitterListItemComponent', () => {
  let component: SubmitterListItemComponent;
  let fixture: ComponentFixture<SubmitterListItemComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SubmitterListItemComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SubmitterListItemComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
