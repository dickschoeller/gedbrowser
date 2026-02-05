import { NO_ERRORS_SCHEMA } from '@angular/core';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';
import { RouterTestingModule } from '@angular/router/testing';
import { vi } from 'vitest';
import { of } from 'rxjs';

import { MainMenuComponent } from './main-menu.component';
import { AuthService, UserService, AuthApiService, ConfigService } from '../../services';

describe('MainMenuComponent', () => {
  let component: MainMenuComponent;
  let fixture: ComponentFixture<MainMenuComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
    schemas: [NO_ERRORS_SCHEMA],
    imports: [NoopAnimationsModule, RouterTestingModule, MainMenuComponent],
    providers: [
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
