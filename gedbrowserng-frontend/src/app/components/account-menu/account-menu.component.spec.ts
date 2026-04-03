import { NO_ERRORS_SCHEMA } from '@angular/core';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter, Router } from '@angular/router';
import { provideHttpClient } from '@angular/common/http';
import { provideHttpClientTesting } from '@angular/common/http/testing';
import { of } from 'rxjs';
import { vi } from 'vitest';

import { AccountMenuComponent } from './account-menu.component';
import { AuthService, UserService } from '../../services';

describe('AccountMenuComponent', () => {
  let component: AccountMenuComponent;
  let fixture: ComponentFixture<AccountMenuComponent>;
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
    imports: [AccountMenuComponent],
    providers: [
      provideRouter([]),
      provideHttpClient(),
      provideHttpClientTesting(),
      { provide: AuthService, useValue: mockAuthService },
      { provide: UserService, useValue: mockUserService }
    ]
})
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AccountMenuComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should initialize user from user service', () => {
    const user = { firstname: 'Grace', lastname: 'Hopper' } as any;
    mockUserService.currentUser = user;

    component.ngOnInit();

    expect(component.user).toBe(user);
  });

  it('should return current router url', () => {
    const router: any = TestBed.inject(Router);
    Object.defineProperty(router, 'url', { get: () => '/profile' });

    expect(component.currentUrl()).toBe('/profile');
  });

  it('should logout and navigate to current url', () => {
    const router: any = TestBed.inject(Router);
    const navigateSpy = vi.spyOn(router, 'navigate').mockResolvedValue(true);
    vi.spyOn(component, 'currentUrl').mockReturnValue('/dataset/persons');

    component.logout();

    expect(mockAuthService.logout).toHaveBeenCalled();
    expect(navigateSpy).toHaveBeenCalledWith(['/dataset/persons']);
  });
});
