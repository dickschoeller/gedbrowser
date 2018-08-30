import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { MultimediaGalleryComponent } from './multimedia-gallery.component';

describe('MultimediaGalleryComponent', () => {
  let component: MultimediaGalleryComponent;
  let fixture: ComponentFixture<MultimediaGalleryComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ MultimediaGalleryComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(MultimediaGalleryComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
