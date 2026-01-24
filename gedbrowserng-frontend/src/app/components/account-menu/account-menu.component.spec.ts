import { NO_ERRORS_SCHEMA } from '@angular/core';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { RouterTestingModule } from '@angular/router/testing';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';

import { AccountMenuComponent } from './account-menu.component';
import { AuthService, UserService } from '../../services';

describe('AccountMenuComponent', () => {
  let component: AccountMenuComponent;
  let fixture: ComponentFixture<AccountMenuComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      schemas: [NO_ERRORS_SCHEMA],
      declarations: [ AccountMenuComponent ],
      imports: [ RouterTestingModule, HttpClientTestingModule, NoopAnimationsModule ],
      providers: [ AuthService, UserService ]
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
