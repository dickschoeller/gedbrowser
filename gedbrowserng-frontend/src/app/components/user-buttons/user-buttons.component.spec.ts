import { NO_ERRORS_SCHEMA } from '@angular/core';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter, Router } from '@angular/router';
import { provideHttpClient } from '@angular/common/http';
import { provideHttpClientTesting } from '@angular/common/http/testing';
import { of } from 'rxjs';
import { vi } from 'vitest';

import { UserButtonsComponent } from './user-buttons.component';
import { AuthService, UserService, AuthApiService, ConfigService } from '../../services';

describe('UserButtonsComponent', () => {
  let component: UserButtonsComponent;
  let fixture: ComponentFixture<UserButtonsComponent>;
  let mockAuthService: any;
  let mockUserService: any;

  beforeEach(() => {
    mockAuthService = {
      logout: vi.fn(() => of(null))
    };
    mockUserService = {
      currentUser: null
    };

    TestBed.configureTestingModule({
    schemas: [NO_ERRORS_SCHEMA],
    imports: [UserButtonsComponent],
    providers: [
      provideRouter([]),
      provideHttpClient(),
      provideHttpClientTesting(),
      { provide: AuthService, useValue: mockAuthService },
      { provide: UserService, useValue: mockUserService },
      AuthApiService,
      ConfigService
    ]
})
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(UserButtonsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should return signed-in state from user service', () => {
    mockUserService.currentUser = { firstname: 'Ada', lastname: 'Lovelace' };
    expect(component.hasSignedIn()).toBe(true);

    mockUserService.currentUser = null;
    expect(component.hasSignedIn()).toBe(false);
  });

  it('should build the user full name', () => {
    mockUserService.currentUser = { firstname: 'Ada', lastname: 'Lovelace' };
    expect(component.userName()).toBe('Ada Lovelace');
  });

  it('should return current router url', () => {
    const router: any = TestBed.inject(Router);
    Object.defineProperty(router, 'url', { get: () => '/dataset/persons' });
    expect(component.currentUrl()).toBe('/dataset/persons');
  });

  it('should logout and navigate to login', () => {
    const router: any = TestBed.inject(Router);
    const navigateSpy = vi.spyOn(router, 'navigate').mockResolvedValue(true);

    component.logout();

    expect(mockAuthService.logout).toHaveBeenCalled();
    expect(navigateSpy).toHaveBeenCalledWith(['/login']);
  });
});
