import { TestBed } from '@angular/core/testing';
import { provideHttpClient } from '@angular/common/http';
import { provideHttpClientTesting } from '@angular/common/http/testing';
import { describe, it, expect, beforeEach } from 'vitest';

import { ConfigService } from './config.service';

describe('ConfigService', () => {
  let service: ConfigService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [],
    providers: [
        provideHttpClient(),
        provideHttpClientTesting()
    ],
      providers: [ ConfigService ]
    });
    service = TestBed.inject(ConfigService);
  });

  describe('service creation', () => {
    it('should be created', () => {
      expect(service).toBeTruthy();
    });
  });

  describe('API URL getters', () => {
    it('should return reset_credentials_url', () => {
      expect(service.reset_credentials_url).toBe('/gedbrowserng/v1/user/reset-credentials');
    });

    it('should return refresh_token_url', () => {
      expect(service.refresh_token_url).toBe('/gedbrowserng/v1/refresh');
    });

    it('should return whoami_url', () => {
      expect(service.whoami_url).toBe('/gedbrowserng/v1/whoami');
    });

    it('should return users_url', () => {
      expect(service.users_url).toBe('/gedbrowserng/v1/user/all');
    });

    it('should return login_url', () => {
      expect(service.login_url).toBe('/gedbrowserng/v1/login');
    });

    it('should return logout_url', () => {
      expect(service.logout_url).toBe('/gedbrowserng/v1/logout');
    });

    it('should return change_password_url', () => {
      expect(service.change_password_url).toBe('/gedbrowserng/v1/changePassword');
    });

    it('should return foo_url', () => {
      expect(service.foo_url).toBe('/gedbrowserng/v1/foo');
    });

    it('should return signup_url', () => {
      expect(service.signup_url).toBe('/gedbrowserng/v1/signup');
    });
  });

  describe('URL consistency', () => {
    it('all URLs should use correct API base path', () => {
      const urls = [
        service.reset_credentials_url,
        service.refresh_token_url,
        service.whoami_url,
        service.users_url,
        service.login_url,
        service.logout_url,
        service.change_password_url,
        service.foo_url,
        service.signup_url
      ];

      urls.forEach(url => {
        expect(url).toContain('/gedbrowserng/v1');
      });
    });
  });
});
