import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { MultimediaButtonComponent } from './multimedia-add-button.component';

describe('MultimediaButtonComponent', () => {
  let component: MultimediaButtonComponent;
  let fixture: ComponentFixture<MultimediaButtonComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ MultimediaButtonComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(MultimediaButtonComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
