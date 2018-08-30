import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { MultimediaDialogComponent } from './multimedia-dialog.component';

describe('MultimediaDialogComponent', () => {
  let component: MultimediaDialogComponent;
  let fixture: ComponentFixture<MultimediaDialogComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ MultimediaDialogComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(MultimediaDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
