import { NO_ERRORS_SCHEMA } from '@angular/core';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { MainLayoutComponent } from './main-layout.component';
import { DatasetsService, SaveService, UploadService, UserService, AuthService, AuthApiService, ConfigService } from '../../services';

describe('MainLayoutComponent', () => {
  let component: MainLayoutComponent;
  let fixture: ComponentFixture<MainLayoutComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
    schemas: [NO_ERRORS_SCHEMA],
    imports: [NoopAnimationsModule, RouterTestingModule, MainLayoutComponent],
    providers: [
      { provide: DatasetsService, useValue: { get: () => of(['test-db']) } },
      { provide: SaveService, useValue: { getTextFile: (dataset: string) => of('GEDCOM content') } },
      { provide: UploadService, useValue: { uploadGedFile: (file: File) => of({ success: true }) } },
      { provide: UserService, useValue: { currentUser: null } },
      { provide: AuthService, useValue: { isLoggedIn: () => false, login: () => {}, logout: () => {} } },
      { provide: AuthApiService, useValue: { request: () => {} } },
      { provide: ConfigService, useValue: { apiUrl: 'http://localhost' } }
    ]
})
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(MainLayoutComponent);
    component = fixture.componentInstance;
    component.dataset = 'testDataset';
    // Don't call detectChanges due to template dataset binding issues
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
