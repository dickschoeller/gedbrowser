import { NO_ERRORS_SCHEMA } from '@angular/core';
import {waitForAsync, ComponentFixture, TestBed} from '@angular/core/testing';
import {HttpClientTestingModule} from '@angular/common/http/testing';
import {RouterTestingModule} from '@angular/router/testing';
import {NoopAnimationsModule} from '@angular/platform-browser/animations';

import {PersonService} from '../../services';
import {PersonListComponent} from './person-list.component';
import {PersonListResolverService} from './person-list-resolver.service';

describe('PersonListComponent', () => {
  let component: PersonListComponent;
  let fixture: ComponentFixture<PersonListComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      schemas: [NO_ERRORS_SCHEMA],
      declarations: [
        PersonListComponent
      ],
      imports: [
        HttpClientTestingModule,
        RouterTestingModule,
        NoopAnimationsModule,
      ],
      providers: [
        PersonService,
        PersonListResolverService,
      ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(PersonListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
