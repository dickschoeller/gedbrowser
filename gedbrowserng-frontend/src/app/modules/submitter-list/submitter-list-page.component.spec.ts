import { NO_ERRORS_SCHEMA } from '@angular/core';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { RouterTestingModule } from '@angular/router/testing';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';

import { SubmitterListPageComponent } from './submitter-list-page.component';
import { SubmitterService } from '../../services';

describe('SubmitterListPageComponent', () => {
  let component: SubmitterListPageComponent;
  let fixture: ComponentFixture<SubmitterListPageComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      schemas: [NO_ERRORS_SCHEMA],
      declarations: [ SubmitterListPageComponent ],
      imports: [ HttpClientTestingModule, RouterTestingModule, NoopAnimationsModule ],
      providers: [ SubmitterService ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(SubmitterListPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
