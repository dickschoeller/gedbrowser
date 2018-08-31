import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { MultimediaEditButtonComponent } from './multimedia-edit-button.component';

describe('MultimediaEditButtonComponent', () => {
  let component: MultimediaEditButtonComponent;
  let fixture: ComponentFixture<MultimediaEditButtonComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ MultimediaEditButtonComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(MultimediaEditButtonComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
