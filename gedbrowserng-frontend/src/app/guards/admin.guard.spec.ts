import { ActivatedRouteSnapshot, RouterStateSnapshot } from '@angular/router';
import { AdminGuard } from './admin.guard';
import { vi } from 'vitest';

describe('AdminGuard', () => {
  let guard: AdminGuard;
  let mockUserService: any;
  let mockRouter: any;

  beforeEach(() => {
    mockUserService = {
      currentUser: null
    };
    mockRouter = {
      navigate: vi.fn()
    };

    guard = new AdminGuard(mockRouter, mockUserService);
  });

  it('should be created', () => {
    expect(guard).toBeTruthy();
  });

  it('should allow access when user has ROLE_ADMIN', () => {
    mockUserService.currentUser = { roles: ['ROLE_ADMIN', 'ROLE_USER'] };

    const route = {} as ActivatedRouteSnapshot;
    const state = { url: '/admin' } as RouterStateSnapshot;

    expect(guard.canActivate(route, state)).toBe(true);
  });

  it('should deny access when user does not have ROLE_ADMIN', () => {
    mockUserService.currentUser = { roles: ['ROLE_USER'] };

    const route = {} as ActivatedRouteSnapshot;
    const state = { url: '/admin' } as RouterStateSnapshot;

    expect(guard.canActivate(route, state)).toBe(false);
    expect(mockRouter.navigate).toHaveBeenCalledWith(['/403']);
  });

  it('should redirect to login when user is not logged in', () => {
    mockUserService.currentUser = null;

    const route = {} as ActivatedRouteSnapshot;
    const state = { url: '/admin' } as RouterStateSnapshot;

    expect(guard.canActivate(route, state)).toBe(false);
    expect(mockRouter.navigate).toHaveBeenCalledWith(['/login'], { queryParams: { returnUrl: '/admin' } });
  });
});
