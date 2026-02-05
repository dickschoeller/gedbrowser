import { NO_ERRORS_SCHEMA } from '@angular/core';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { RouterTestingModule } from '@angular/router/testing';
import { HttpClientTestingModule } from '@angular/common/http/testing';

import { UserButtonsComponent } from './user-buttons.component';
import { AuthService, UserService, AuthApiService, ConfigService } from '../../services';

describe('UserButtonsComponent', () => {
  let component: UserButtonsComponent;
  let fixture: ComponentFixture<UserButtonsComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
    schemas: [NO_ERRORS_SCHEMA],
    imports: [RouterTestingModule, HttpClientTestingModule, UserButtonsComponent],
    providers: [AuthService, UserService, AuthApiService, ConfigService]
})
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(UserButtonsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
