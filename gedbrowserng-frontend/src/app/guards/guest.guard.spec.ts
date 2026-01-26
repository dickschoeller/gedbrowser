import { Router } from '@angular/router';
import { GuestGuard } from './guest.guard';
import { UserService } from '../services';
import { vi } from 'vitest';

describe('GuestGuard', () => {
  let guard: GuestGuard;
  let mockUserService: any;
  let mockRouter: any;

  beforeEach(() => {
    mockUserService = {
      currentUser: null
    };
    mockRouter = {
      navigate: vi.fn()
    };

    guard = new GuestGuard(mockRouter, mockUserService);
  });

  it('should be created', () => {
    expect(guard).toBeTruthy();
  });

  it('should allow access when user is not logged in', () => {
    mockUserService.currentUser = null;

    expect(guard.canActivate()).toBe(true);
  });

  it('should deny access and redirect home when user is logged in', () => {
    mockUserService.currentUser = { id: '1', name: 'Test User' };

    expect(guard.canActivate()).toBe(false);
    expect(mockRouter.navigate).toHaveBeenCalledWith(['/']);
  });
});
