import { NO_ERRORS_SCHEMA } from '@angular/core';
import {waitForAsync, ComponentFixture, TestBed} from '@angular/core/testing';
import {HttpClientTestingModule} from '@angular/common/http/testing';
import {RouterTestingModule} from '@angular/router/testing';
import {NoopAnimationsModule} from '@angular/platform-browser/animations';

import {SubmitterService} from '../../services';
import {SubmitterListComponent} from './submitter-list.component';

describe('SubmitterListComponent', () => {
  let component: SubmitterListComponent;
  let fixture: ComponentFixture<SubmitterListComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      schemas: [NO_ERRORS_SCHEMA],
      declarations: [
        SubmitterListComponent
      ],
      imports: [
        RouterTestingModule,
        NoopAnimationsModule,
        HttpClientTestingModule,
      ],
      providers: [
        SubmitterService,
      ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(SubmitterListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
