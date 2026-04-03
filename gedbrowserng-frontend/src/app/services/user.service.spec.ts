import { TestBed } from '@angular/core/testing';
import { provideHttpClient } from '@angular/common/http';
import { provideHttpClientTesting } from '@angular/common/http/testing';
import { describe, it, expect, beforeEach, vi } from 'vitest';

import { UserService, User } from './user.service';
import { AuthApiService } from './auth-api.service';
import { ConfigService } from './config.service';
import { firstValueFrom, of, throwError } from 'rxjs';

describe('UserService', () => {
  let service: UserService;
  let mockAuthApiService: any;
  let mockConfigService: any;

  const mockUser: User = {
    username: 'testuser',
    firstname: 'Test',
    lastname: 'User',
    email: 'test@example.com',
    password: 'password',
    roles: ['USER']
  };

  beforeEach(() => {
    mockAuthApiService = {
      get: vi.fn()
    };

    mockConfigService = {
      refresh_token_url: '/api/refresh',
      whoami_url: '/api/whoami',
      reset_credentials_url: '/api/reset',
      users_url: '/api/users'
    };

    TestBed.configureTestingModule({
      providers: [
        provideHttpClient(),
        provideHttpClientTesting(),
        UserService,
        { provide: AuthApiService, useValue: mockAuthApiService },
        { provide: ConfigService, useValue: mockConfigService }
      ]
    });

    service = TestBed.inject(UserService);
  });

  describe('service creation', () => {
    it('should be created', () => {
      expect(service).toBeTruthy();
    });

    it('should have undefined currentUser initially', () => {
      expect(service.currentUser).toBeUndefined();
    });
  });

  describe('initUser()', () => {
    it('initializes user with valid access token', async () => {
      const refreshResponse = { accessToken: 'valid-token' };
      
      mockAuthApiService.get.mockReturnValue(of(refreshResponse));
      vi.spyOn(service, 'getMyInfo').mockReturnValue(of(mockUser));

      await service.initUser();

      expect(service.currentUser).toEqual(mockUser);
    });

    it('returns null when accessToken is null', async () => {
      const refreshResponse = { accessToken: null };
      mockAuthApiService.get.mockReturnValue(of(refreshResponse));

      const result = await service.initUser();

      expect(result).toBeNull();
    });

    it('returns null when accessToken is undefined', async () => {
      const refreshResponse = { accessToken: undefined };
      mockAuthApiService.get.mockReturnValue(of(refreshResponse));

      const result = await service.initUser();

      expect(result).toBeNull();
    });

    it('returns null when refresh request fails', async () => {
      mockAuthApiService.get.mockReturnValue(
        throwError(() => new Error('Network error'))
      );

      const result = await service.initUser();

      expect(result).toBeNull();
    });

    it('calls getMyInfo when refresh succeeds with valid token', async () => {
      const refreshResponse = { accessToken: 'token123' };
      mockAuthApiService.get.mockReturnValue(of(refreshResponse));
      vi.spyOn(service, 'getMyInfo').mockReturnValue(of(mockUser));

      await service.initUser();

      expect(service.getMyInfo).toHaveBeenCalled();
    });

    it('sets currentUser from getMyInfo response', async () => {
      const refreshResponse = { accessToken: 'token123' };
      const newUser = { ...mockUser, username: 'newuser' };
      
      mockAuthApiService.get.mockReturnValue(of(refreshResponse));
      vi.spyOn(service, 'getMyInfo').mockReturnValue(of(newUser));

      await service.initUser();

      expect(service.currentUser).toEqual(newUser);
    });
  });

  describe('resetCredentials()', () => {
    it('calls authApiService.get with correct URL', () => {
      mockAuthApiService.get.mockReturnValue(of({}));

      service.resetCredentials();

      expect(mockAuthApiService.get).toHaveBeenCalledWith('/api/reset');
    });

    it('returns observable', () => {
      mockAuthApiService.get.mockReturnValue(of({ status: 'ok' }));

      const result = service.resetCredentials();

      expect(result).toBeDefined();
    });
  });

  describe('getMyInfo()', () => {
    it('calls authApiService.get with whoami URL', () => {
      mockAuthApiService.get.mockReturnValue(of(mockUser));

      service.getMyInfo();

      expect(mockAuthApiService.get).toHaveBeenCalledWith('/api/whoami');
    });

    it('returns user observable', async () => {
      mockAuthApiService.get.mockReturnValue(of(mockUser));

      const result = await firstValueFrom(service.getMyInfo());
      expect(result).toEqual(mockUser);
    });

    it('updates currentUser', async () => {
      mockAuthApiService.get.mockReturnValue(of(mockUser));

      await firstValueFrom(service.getMyInfo());
      expect(service.currentUser).toEqual(mockUser);
    });

    it('handles error', async () => {
      const error = new Error('Failed');
      mockAuthApiService.get.mockReturnValue(throwError(() => error));

      await expect(firstValueFrom(service.getMyInfo())).rejects.toEqual(error);
    });
  });

  describe('getAll()', () => {
    it('calls authApiService.get with users URL', () => {
      mockAuthApiService.get.mockReturnValue(of([]));

      service.getAll();

      expect(mockAuthApiService.get).toHaveBeenCalledWith('/api/users');
    });

    it('returns users array', async () => {
      const users = [mockUser];
      mockAuthApiService.get.mockReturnValue(of(users));

      const result = await firstValueFrom(service.getAll());
      expect(result).toEqual(users);
    });

    it('returns empty array when no users', async () => {
      mockAuthApiService.get.mockReturnValue(of([]));

      const result = await firstValueFrom(service.getAll());
      expect(result.length).toBe(0);
    });

    it('handles error', async () => {
      const error = new Error('Fetch failed');
      mockAuthApiService.get.mockReturnValue(throwError(() => error));

      await expect(firstValueFrom(service.getAll())).rejects.toEqual(error);
    });
  });
});
