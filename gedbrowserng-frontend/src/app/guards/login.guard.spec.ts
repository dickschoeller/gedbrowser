import { LoginGuard } from './login.guard';
import { vi } from 'vitest';

describe('LoginGuard', () => {
  let guard: LoginGuard;
  let mockUserService: any;
  let mockRouter: any;

  beforeEach(() => {
    mockUserService = {
      currentUser: null
    };
    mockRouter = {
      navigate: vi.fn()
    };

    guard = new LoginGuard(mockRouter, mockUserService);
  });

  it('should be created', () => {
    expect(guard).toBeTruthy();
  });

  it('should allow access when user is logged in', () => {
    mockUserService.currentUser = { id: '1', name: 'Test User' };

    expect(guard.canActivate()).toBe(true);
  });

  it('should deny access and redirect home when user is not logged in', () => {
    mockUserService.currentUser = null;

    expect(guard.canActivate()).toBe(false);
    expect(mockRouter.navigate).toHaveBeenCalledWith(['/']);
  });
});
