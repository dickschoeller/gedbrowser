import { NO_ERRORS_SCHEMA } from '@angular/core';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { MatDialogModule } from '@angular/material/dialog';
import { NgxGalleryModule } from 'ngx-gallery-15';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';
import { HttpClientTestingModule } from '@angular/common/http/testing';

import { MultimediaGalleryComponent } from './multimedia-gallery.component';
import { UserService, AuthApiService, ConfigService } from '../../services';

describe('MultimediaGalleryComponent', () => {
  let component: MultimediaGalleryComponent;
  let fixture: ComponentFixture<MultimediaGalleryComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      schemas: [NO_ERRORS_SCHEMA],
      declarations: [ MultimediaGalleryComponent ],
      imports: [ MatDialogModule, NgxGalleryModule, NoopAnimationsModule, HttpClientTestingModule ],
      providers: [ UserService, AuthApiService, ConfigService ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(MultimediaGalleryComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
