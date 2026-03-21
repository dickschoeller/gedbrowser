import { TestBed } from '@angular/core/testing';
import { Router, provideRouter } from '@angular/router';
import { of, throwError } from 'rxjs';
import { vi } from 'vitest';

import { AppComponent } from './app.component';
import { PersonService, UserService } from './services';

describe('AppComponent', () => {
  let mockUserService: any;
  let mockPersonService: any;
  let router: Router;

  beforeEach(() => {
    mockUserService = {
      currentUser: null,
      getMyInfo: vi.fn()
    };
    mockPersonService = {
      getOne: vi.fn()
    };

    TestBed.configureTestingModule({
      imports: [AppComponent],
      providers: [
        provideRouter([]),
        { provide: UserService, useValue: mockUserService },
        { provide: PersonService, useValue: mockPersonService }
      ]
    });

    router = TestBed.inject(Router);
  });

  it('should create the app', () => {
    const fixture = TestBed.createComponent(AppComponent);
    const app = fixture.debugElement.componentInstance;
    expect(app).toBeTruthy();
  });

  it('does not call whoami if no user is signed in', () => {
    const fixture = TestBed.createComponent(AppComponent);
    const app = fixture.debugElement.componentInstance;

    app.checkSessionStillValid();

    expect(mockUserService.getMyInfo).not.toHaveBeenCalled();
  });

  it('refreshes current user when session is still valid', () => {
    const refreshedUser = { firstname: 'Ada', lastname: 'Lovelace' };
    mockUserService.currentUser = { firstname: 'Old', lastname: 'Name' };
    mockUserService.getMyInfo.mockReturnValue(of(refreshedUser));

    const fixture = TestBed.createComponent(AppComponent);
    const app = fixture.debugElement.componentInstance;
    app.checkSessionStillValid();

    expect(mockUserService.currentUser).toEqual(refreshedUser);
  });

  it('clears user on auth failure', () => {
    mockUserService.currentUser = { firstname: 'Signed', lastname: 'In' };
    mockUserService.getMyInfo.mockReturnValue(throwError(() => ({ status: 401 })));
    Object.defineProperty(router, 'url', { get: () => '/demo/persons/abc', configurable: true });
    mockPersonService.getOne.mockReturnValue(of({}));
    const navigateSpy = vi.spyOn(router, 'navigateByUrl').mockResolvedValue(true as any);

    const fixture = TestBed.createComponent(AppComponent);
    const app = fixture.debugElement.componentInstance;
    app.checkSessionStillValid();

    expect(mockUserService.currentUser).toBeNull();
    expect(navigateSpy).toHaveBeenCalledWith('/demo/persons/abc', { replaceUrl: true });
  });

  it('redirects to person list when person is hidden after session expiry', () => {
    mockUserService.currentUser = { firstname: 'Signed', lastname: 'In' };
    mockUserService.getMyInfo.mockReturnValue(throwError(() => ({ status: 401 })));
    Object.defineProperty(router, 'url', { get: () => '/demo/persons/private-person', configurable: true });
    mockPersonService.getOne.mockReturnValue(throwError(() => ({ status: 404 })));
    const navigateSpy = vi.spyOn(router, 'navigateByUrl').mockResolvedValue(true as any);

    const fixture = TestBed.createComponent(AppComponent);
    const app = fixture.debugElement.componentInstance;
    app.checkSessionStillValid();

    expect(mockPersonService.getOne).toHaveBeenCalledWith('demo', 'private-person');
    expect(navigateSpy).toHaveBeenCalledWith('/demo/persons', { replaceUrl: true });
  });

  it('refreshes current page when current page is not a person page', () => {
    mockUserService.currentUser = { firstname: 'Signed', lastname: 'In' };
    mockUserService.getMyInfo.mockReturnValue(throwError(() => ({ status: 401 })));
    Object.defineProperty(router, 'url', { get: () => '/demo/notes', configurable: true });
    const navigateSpy = vi.spyOn(router, 'navigateByUrl').mockResolvedValue(true as any);

    const fixture = TestBed.createComponent(AppComponent);
    const app = fixture.debugElement.componentInstance;
    app.checkSessionStillValid();

    expect(mockPersonService.getOne).not.toHaveBeenCalled();
    expect(navigateSpy).toHaveBeenCalledWith('/demo/notes', { replaceUrl: true });
  });
});
