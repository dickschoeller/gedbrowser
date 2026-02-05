import { NO_ERRORS_SCHEMA } from '@angular/core';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { RouterTestingModule } from '@angular/router/testing';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';
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
    imports: [RouterTestingModule, HttpClientTestingModule, NoopAnimationsModule, AccountMenuComponent],
    providers: [
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
});
