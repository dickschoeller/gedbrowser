import { NO_ERRORS_SCHEMA } from '@angular/core';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ReactiveFormsModule, FormsModule } from '@angular/forms';
import { provideHttpClient } from '@angular/common/http';
import { provideHttpClientTesting } from '@angular/common/http/testing';
import { Router, ActivatedRoute, provideRouter, convertToParamMap } from '@angular/router';
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

  beforeEach(async () => {
    paramMapSubject = new BehaviorSubject<any>(convertToParamMap({}));
    queryParamMapSubject = new BehaviorSubject<any>(convertToParamMap({}));

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

  it('sets return url from route params when query params do not provide it', async () => {
    queryParamMapSubject.next({
      get: (_key: string) => null
    });
    paramMapSubject.next({
      get: (key: string) => key === 'returnUrl' ? '/protected' : null
    });
    await new Promise(resolve => setTimeout(resolve, 0));
    expect(component.returnUrl).toBe('/protected');
  });

  it('defaults return url to / when not provided', async () => {
    queryParamMapSubject.next({
      get: (_key: string) => null
    });
    paramMapSubject.next({
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
    
      queryParamMap: of(convertToParamMap({})),
      queryParams: of({}),

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
      queryParamMap: of(convertToParamMap({})),
      queryParams: of({}),
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
