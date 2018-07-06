import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SubmittersComponent } from './submitters.component';

describe('SubmittersComponent', () => {
  let component: SubmittersComponent;
  let fixture: ComponentFixture<SubmittersComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SubmittersComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SubmittersComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
