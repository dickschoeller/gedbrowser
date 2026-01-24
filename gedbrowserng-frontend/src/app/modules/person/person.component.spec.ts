import { NO_ERRORS_SCHEMA } from '@angular/core';
import {waitForAsync, ComponentFixture, TestBed} from '@angular/core/testing';
import { RouterTestingModule } from '@angular/router/testing';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';

import { PersonComponent } from './person.component';
import { PersonService } from '../../services';

describe('PersonComponent', () => {
  let component: PersonComponent;
  let fixture: ComponentFixture<PersonComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      schemas: [NO_ERRORS_SCHEMA],
      declarations: [ PersonComponent ],
      imports: [ RouterTestingModule, HttpClientTestingModule, NoopAnimationsModule ],
      providers: [ PersonService ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(PersonComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
