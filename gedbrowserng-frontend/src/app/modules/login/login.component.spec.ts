import { NO_ERRORS_SCHEMA } from '@angular/core';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ReactiveFormsModule, FormsModule, FormBuilder } from '@angular/forms';
import { provideHttpClient } from '@angular/common/http';
import { provideHttpClientTesting } from '@angular/common/http/testing';
import { Router, ActivatedRoute, provideRouter } from '@angular/router';
import { of, throwError, BehaviorSubject } from 'rxjs';

import { LoginComponent } from './login.component';
import { AuthService, UserService, AuthApiService, ConfigService } from '../../services';

describe('LoginComponent', () => {
  let component: LoginComponent;
  let fixture: ComponentFixture<LoginComponent>;
  let authService: AuthService;
  let userService: UserService;
  let router: Router;
  let paramMapSubject: BehaviorSubject<any>;
  let queryParamMapSubject: BehaviorSubject<any>;

  const createComponentWithRoute = (routeStub: Partial<ActivatedRoute>) => {
    const manualUserService = {
      getMyInfo: vi.fn().mockReturnValue(of({})),
      resetCredentials: vi.fn().mockReturnValue(of({ result: 'success' }))
    } as unknown as UserService;
    const manualAuthService = {
      login: vi.fn().mockReturnValue(of({}))
    } as unknown as AuthService;
    const manualRouter = {
      navigate: vi.fn()
    } as unknown as Router;

    return new LoginComponent(
      manualUserService,
      manualAuthService,
      manualRouter,
      routeStub as ActivatedRoute,
      TestBed.inject(FormBuilder)
    );
  };

  beforeEach(async () => {
    paramMapSubject = new BehaviorSubject<any>({
      get: (_key: string) => null
    });
    queryParamMapSubject = new BehaviorSubject<any>({
      get: (_key: string) => null
    });

    await TestBed.configureTestingModule({
    schemas: [NO_ERRORS_SCHEMA],
    imports: [ReactiveFormsModule, FormsModule, LoginComponent],
    providers: [
      provideRouter([]),
      provideHttpClient(),
      provideHttpClientTesting(),
      AuthService,
      UserService,
      AuthApiService,
      ConfigService,
      {
        provide: ActivatedRoute,
        useValue: {
          paramMap: paramMapSubject.asObservable(),
          queryParamMap: queryParamMapSubject.asObservable(),
          snapshot: {
            paramMap: { get: (_key: string) => null }
          }
        }
      }
    ]
  })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(LoginComponent);
    component = fixture.componentInstance;
    authService = TestBed.inject(AuthService);
    userService = TestBed.inject(UserService);
    router = TestBed.inject(Router);
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('initializes form with validators', () => {
    expect(component.form).toBeDefined();
    expect(component.form.get('username')).toBeDefined();
    expect(component.form.get('password')).toBeDefined();
  });

  it('form is invalid when empty', () => {
    expect(component.form.valid).toBeFalsy();
  });

  it('username field validity', () => {
    const username = component.form.get('username');
    expect(username?.valid).toBeFalsy();
    
    username?.setValue('ab');
    expect(username?.valid).toBeFalsy(); // too short
    
    username?.setValue('validuser');
    expect(username?.valid).toBeTruthy();
  });

  it('password field validity', () => {
    const password = component.form.get('password');
    expect(password?.valid).toBeFalsy();
    
    password?.setValue('ab');
    expect(password?.valid).toBeFalsy(); // too short
    
    password?.setValue('validpass');
    expect(password?.valid).toBeTruthy();
  });

  it('sets return url from query params', async () => {
    queryParamMapSubject.next({
      get: (key: string) => key === 'returnUrl' ? '/dashboard' : null
    });
    await new Promise(resolve => setTimeout(resolve, 0));
    expect(component.returnUrl).toBe('/dashboard');
  });

  it('defaults return url to / when not provided', async () => {
    queryParamMapSubject.next({
      get: (_key: string) => null
    });
    await new Promise(resolve => setTimeout(resolve, 0));
    expect(component.returnUrl).toBe('/');
  });

  it('sets notification from paramMap msgType and msgBody', async () => {
    paramMapSubject.next({
      get: (key: string) => key === 'msgType' ? 'info' : key === 'msgBody' ? 'Test message' : null
    });
    await new Promise(resolve => setTimeout(resolve, 0));
    expect(component.notification).toEqual({ msgType: 'info', msgBody: 'Test message' });
  });

  it('clears notification when paramMap has no msgType or msgBody', async () => {
    paramMapSubject.next({
      get: (_key: string) => null
    });
    await new Promise(resolve => setTimeout(resolve, 0));
    expect(component.notification).toBeUndefined();
  });

  it('clears notification when paramMap has msgType but no msgBody', async () => {
    paramMapSubject.next({
      get: (key: string) => key === 'msgType' ? 'error' : null
    });
    await new Promise(resolve => setTimeout(resolve, 0));
    expect(component.notification).toBeUndefined();
  });

  it('uses legacy route params when paramMap is unavailable', () => {
    const legacyComponent = createComponentWithRoute({
      params: of({ msgType: 'info', msgBody: 'legacy message' }),
      queryParamMap: of({ get: (_key: string) => null }),
      snapshot: {
        paramMap: { get: (_key: string) => null },
        params: {}
      }
    });

    legacyComponent.ngOnInit();

    expect(legacyComponent.notification).toEqual({
      msgType: 'info',
      msgBody: 'legacy message'
    });
  });

  it('uses queryParams when queryParamMap is unavailable', () => {
    const legacyComponent = createComponentWithRoute({
      paramMap: of({ get: (_key: string) => null }),
      queryParams: of({ returnUrl: '/from-query-params' }),
      snapshot: {
        paramMap: { get: (_key: string) => '/from-snapshot-param-map' },
        params: { returnUrl: '/from-snapshot-params' }
      }
    });

    legacyComponent.ngOnInit();

    expect(legacyComponent.returnUrl).toBe('/from-query-params');
  });

  it('uses snapshot paramMap when query streams are unavailable', () => {
    const legacyComponent = createComponentWithRoute({
      paramMap: of({ get: (_key: string) => null }),
      snapshot: {
        queryParamMap: { get: (_key: string) => null },
        paramMap: { get: (key: string) => key === 'returnUrl' ? '/from-snapshot-param-map' : null },
        params: {}
      }
    });

    legacyComponent.ngOnInit();

    expect(legacyComponent.returnUrl).toBe('/from-snapshot-param-map');
  });

  it('uses snapshot params when snapshot paramMap has no returnUrl', () => {
    const legacyComponent = createComponentWithRoute({
      paramMap: of({ get: (_key: string) => null }),
      snapshot: {
        queryParamMap: { get: (_key: string) => null },
        paramMap: { get: (_key: string) => null },
        params: { returnUrl: '/from-snapshot-params' }
      }
    });

    legacyComponent.ngOnInit();

    expect(legacyComponent.returnUrl).toBe('/from-snapshot-params');
  });

  it('defaults to / when no returnUrl sources are present in fallback mode', () => {
    const legacyComponent = createComponentWithRoute({
      paramMap: of({ get: (_key: string) => null }),
      snapshot: {
        queryParamMap: { get: (_key: string) => null },
        paramMap: { get: (_key: string) => null },
        params: {}
      }
    });

    legacyComponent.ngOnInit();

    expect(legacyComponent.returnUrl).toBe('/');
  });

  it('onSubmit sets submitted flag and calls login', async () => {
    vi.useFakeTimers();
    const loginSpy = vi.spyOn(authService, 'login').mockReturnValue(of({}));
    const getMyInfoSpy = vi.spyOn(userService, 'getMyInfo').mockReturnValue(of({}));
    const navigateSpy = vi.spyOn(router, 'navigate');
    
    component.form.setValue({ username: 'testuser', password: 'testpass' });
    component.onSubmit();
    
    expect(component.submitted).toBeTruthy();
    expect(component.notification).toBeUndefined();
    
    await vi.advanceTimersByTimeAsync(1000);
    
    expect(loginSpy).toHaveBeenCalledWith({ username: 'testuser', password: 'testpass' });
    expect(getMyInfoSpy).toHaveBeenCalled();
    expect(navigateSpy).toHaveBeenCalledWith(['/']);
    
    vi.useRealTimers();
  });

  it('onSubmit navigates to returnUrl on success', async () => {
    vi.useFakeTimers();
    component.returnUrl = '/dashboard';
    vi.spyOn(authService, 'login').mockReturnValue(of({}));
    vi.spyOn(userService, 'getMyInfo').mockReturnValue(of({}));
    const navigateSpy = vi.spyOn(router, 'navigate');
    
    component.form.setValue({ username: 'testuser', password: 'testpass' });
    component.onSubmit();
    
    await vi.advanceTimersByTimeAsync(1000);
    
    expect(navigateSpy).toHaveBeenCalledWith(['/dashboard']);
    
    vi.useRealTimers();
  });

  it('onSubmit handles login failure', async () => {
    vi.useFakeTimers();
    vi.spyOn(authService, 'login').mockReturnValue(throwError(() => new Error('Login failed')));
    
    component.form.setValue({ username: 'baduser', password: 'badpass' });
    
    // submitted is set before the observable subscription
    component.onSubmit();
    
    // Wait for the delay and error callback to execute
    await vi.advanceTimersByTimeAsync(1000);
    
    expect(component.submitted).toBeFalsy();
    expect(component.notification).toEqual({
      msgType: 'error',
      msgBody: 'Incorrect username or password.'
    });
    
    vi.useRealTimers();
  });

  it('onResetCredentials calls resetCredentials and shows success alert', () => {
    const alertSpy = vi.spyOn(globalThis, 'alert').mockImplementation(() => {});
    vi.spyOn(userService, 'resetCredentials').mockReturnValue(of({ result: 'success' }));
    
    component.onResetCredentials();
    
    expect(alertSpy).toHaveBeenCalledWith('Password has been reset to 123 for all accounts');
  });

  it('onResetCredentials shows error alert on failure', () => {
    const alertSpy = vi.spyOn(globalThis, 'alert').mockImplementation(() => {});
    vi.spyOn(userService, 'resetCredentials').mockReturnValue(of({ result: 'failure' }));
    
    component.onResetCredentials();
    
    expect(alertSpy).toHaveBeenCalledWith('Server error');
  });

  it('cleans up subscriptions on destroy', () => {
    const nextSpy = vi.spyOn(component['ngUnsubscribe'], 'next');
    const completeSpy = vi.spyOn(component['ngUnsubscribe'], 'complete');
    
    component.ngOnDestroy();
    
    expect(nextSpy).toHaveBeenCalled();
    expect(completeSpy).toHaveBeenCalled();
  });
});
