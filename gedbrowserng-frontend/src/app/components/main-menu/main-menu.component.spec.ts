import { NO_ERRORS_SCHEMA } from '@angular/core';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideAnimations } from '@angular/platform-browser/animations';
import { provideRouter } from '@angular/router';
import { vi } from 'vitest';

import { MainMenuComponent } from './main-menu.component';
import { AuthService, UserService, AuthApiService, ConfigService } from '../../services';

describe('MainMenuComponent', () => {
  let component: MainMenuComponent;
  let fixture: ComponentFixture<MainMenuComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
    schemas: [NO_ERRORS_SCHEMA],
    imports: [MainMenuComponent],
    providers: [
      provideAnimations(),
      provideRouter([]),
      { provide: AuthService, useValue: { isLoggedIn: () => false, login: vi.fn(), logout: vi.fn() } },
      { provide: UserService, useValue: { currentUser: null } },
      { provide: AuthApiService, useValue: { request: vi.fn() } },
      { provide: ConfigService, useValue: { apiUrl: 'http://localhost' } }
    ]
  })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(MainMenuComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
