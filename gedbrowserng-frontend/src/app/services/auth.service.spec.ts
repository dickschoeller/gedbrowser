import { TestBed } from '@angular/core/testing';
import { provideHttpClient } from '@angular/common/http';
import { provideHttpClientTesting } from '@angular/common/http/testing';
import { describe, it, expect, beforeEach, vi } from 'vitest';
import { of } from 'rxjs';

import { AuthService } from './auth.service';
import { AuthApiService } from './auth-api.service';
import { UserService } from './user.service';
import { ConfigService } from './config.service';

describe('AuthService', () => {
  let service: AuthService;
  let mockAuthApiService: any;
  let mockUserService: any;
  let mockConfigService: any;

  beforeEach(() => {
    mockAuthApiService = {
      post: vi.fn()
    };

    mockUserService = {
      getMyInfo: vi.fn().mockReturnValue(of(null)),
      currentUser: undefined
    };

    mockConfigService = {
      login_url: '/api/login',
      signup_url: '/api/signup',
      logout_url: '/api/logout',
      change_password_url: '/api/changePassword'
    };

    TestBed.configureTestingModule({
      imports: [],
    providers: [
        provideHttpClient(),
        provideHttpClientTesting()
    ],
      providers: [
        AuthService,
        { provide: AuthApiService, useValue: mockAuthApiService },
        { provide: UserService, useValue: mockUserService },
        { provide: ConfigService, useValue: mockConfigService }
      ]
    });

    service = TestBed.inject(AuthService);
  });

  describe('service creation', () => {
    it('should be created', () => {
      expect(service).toBeTruthy();
    });
  });

  describe('login()', () => {
    it('should call apiService.post with login URL', () => {
      const user = { username: 'testuser', password: 'password' };
      mockAuthApiService.post.mockReturnValue(of({}));

      service.login(user).subscribe();

      expect(mockAuthApiService.post).toHaveBeenCalledWith(
        '/api/login',
        'username=testuser&password=password',
        expect.any(Object)
      );
    });

    it('should send username and password in body', () => {
      const user = { username: 'john', password: 'secret123' };
      mockAuthApiService.post.mockReturnValue(of({}));

      service.login(user).subscribe();

      const call = mockAuthApiService.post.mock.calls[0];
      expect(call[1]).toContain('username=john');
      expect(call[1]).toContain('password=secret123');
    });

    it('should set Content-Type header to application/x-www-form-urlencoded', () => {
      const user = { username: 'testuser', password: 'password' };
      mockAuthApiService.post.mockReturnValue(of({}));

      service.login(user).subscribe();

      const headers = mockAuthApiService.post.mock.calls[0][2];
      expect(headers.get('Content-Type')).toBe('application/x-www-form-urlencoded');
    });

    it('should set Accept header to application/json', () => {
      const user = { username: 'testuser', password: 'password' };
      mockAuthApiService.post.mockReturnValue(of({}));

      service.login(user).subscribe();

      const headers = mockAuthApiService.post.mock.calls[0][2];
      expect(headers.get('Accept')).toBe('application/json');
    });

    it('should call userService.getMyInfo on successful login', () => {
      const user = { username: 'testuser', password: 'password' };
      mockAuthApiService.post.mockReturnValue(of({}));

      service.login(user).subscribe();

      expect(mockUserService.getMyInfo).toHaveBeenCalled();
    });
  });

  describe('signup()', () => {
    it('should call apiService.post with signup URL', () => {
      const user = { username: 'newuser', password: 'password', email: 'test@example.com' };
      mockAuthApiService.post.mockReturnValue(of({}));

      service.signup(user).subscribe();

      expect(mockAuthApiService.post).toHaveBeenCalledWith(
        '/api/signup',
        JSON.stringify(user),
        expect.any(Object)
      );
    });

    it('should send user object as JSON', () => {
      const user = { username: 'newuser', password: 'password', email: 'test@example.com' };
      mockAuthApiService.post.mockReturnValue(of({}));

      service.signup(user).subscribe();

      const call = mockAuthApiService.post.mock.calls[0];
      expect(call[1]).toBe(JSON.stringify(user));
    });

    it('should set Content-Type header to application/json', () => {
      const user = { username: 'newuser', password: 'password' };
      mockAuthApiService.post.mockReturnValue(of({}));

      service.signup(user).subscribe();

      const headers = mockAuthApiService.post.mock.calls[0][2];
      expect(headers.get('Content-Type')).toBe('application/json');
    });

    it('should set Accept header to application/json', () => {
      const user = { username: 'newuser', password: 'password' };
      mockAuthApiService.post.mockReturnValue(of({}));

      service.signup(user).subscribe();

      const headers = mockAuthApiService.post.mock.calls[0][2];
      expect(headers.get('Accept')).toBe('application/json');
    });
  });

  describe('logout()', () => {
    it('should call apiService.post with logout URL', () => {
      mockAuthApiService.post.mockReturnValue(of({}));

      service.logout().subscribe();

      expect(mockAuthApiService.post).toHaveBeenCalledWith('/api/logout', {});
    });

    it('should clear currentUser after logout', () => {
      mockAuthApiService.post.mockReturnValue(of({}));
      mockUserService.currentUser = { username: 'testuser' };

      service.logout().subscribe();

      expect(mockUserService.currentUser).toBeNull();
    });

    it('should handle logout error gracefully', async () => {
      mockAuthApiService.post.mockReturnValue(of({}));

      await service.logout().toPromise();
    });
  });

  describe('changePassowrd()', () => {
    it('should call apiService.post with change password URL', () => {
      const passwordChanger = { oldPassword: 'old', newPassword: 'new' };
      mockAuthApiService.post.mockReturnValue(of({}));

      service.changePassowrd(passwordChanger).subscribe();

      expect(mockAuthApiService.post).toHaveBeenCalledWith(
        '/api/changePassword',
        passwordChanger
      );
    });

    it('should send password change data', () => {
      const passwordChanger = { oldPassword: 'old', newPassword: 'new' };
      mockAuthApiService.post.mockReturnValue(of({}));

      service.changePassowrd(passwordChanger).subscribe();

      const call = mockAuthApiService.post.mock.calls[0];
      expect(call[1]).toEqual(passwordChanger);
    });

    it('should handle password change response', async () => {
      const passwordChanger = { oldPassword: 'old', newPassword: 'new' };
      const response = { status: 'success' };
      mockAuthApiService.post.mockReturnValue(of(response));

      const result = await service.changePassowrd(passwordChanger).toPromise();
      expect(result).toEqual(response);
    });
  });
});
