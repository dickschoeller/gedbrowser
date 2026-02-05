import { NO_ERRORS_SCHEMA } from '@angular/core';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { MatButtonModule } from '@angular/material/button';
import { MatSelectModule } from '@angular/material/select';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { ReactiveFormsModule, FormsModule } from '@angular/forms';
import { provideHttpClient } from '@angular/common/http';
import { provideHttpClientTesting } from '@angular/common/http/testing';
import { provideAnimations } from '@angular/platform-browser/animations';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { HeadComponent } from './head.component';
import { HeadService, DatasetsService, SaveService, UploadService, UserService, AuthService, AuthApiService, ConfigService } from '../../services';

describe('HeadComponent', () => {
  let component: HeadComponent;
  let fixture: ComponentFixture<HeadComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
    schemas: [NO_ERRORS_SCHEMA],
    imports: [MatButtonModule, MatSelectModule, MatFormFieldModule, MatInputModule, ReactiveFormsModule, FormsModule, HeadComponent],
    providers: [
      provideHttpClient(),
      provideHttpClientTesting(),
      provideAnimations(),
      HeadService,
      { provide: DatasetsService, useValue: { get: () => of(['test-db']) } },
      { provide: SaveService, useValue: { getTextFile: (dataset: string) => of('GEDCOM content') } },
      { provide: UploadService, useValue: { uploadGedFile: (file: File) => of({ success: true }) } },
      { provide: UserService, useValue: { currentUser: null } },
      { provide: AuthService, useValue: { isLoggedIn: () => false, login: () => {}, logout: () => {} } },
      { provide: AuthApiService, useValue: { request: () => {} } },
      { provide: ConfigService, useValue: { apiUrl: 'http://localhost' } },
      {
        provide: ActivatedRoute,
        useValue: {
          data: of({ dataset: 'test', head: {} }),
          params: of({ dataset: 'test' })
        }
      }
    ]
})
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(HeadComponent);
    component = fixture.componentInstance;
    // Don't call detectChanges here - component needs route data first
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
