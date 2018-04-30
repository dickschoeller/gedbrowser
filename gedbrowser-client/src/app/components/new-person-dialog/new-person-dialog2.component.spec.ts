import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { NewPersonDialog2Component } from './new-person-dialog2.component';

describe('NewPersonDialog2Component', () => {
  let component: NewPersonDialog2Component;
  let fixture: ComponentFixture<NewPersonDialog2Component>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ NewPersonDialog2Component ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(NewPersonDialog2Component);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
