import { NO_ERRORS_SCHEMA } from '@angular/core';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { RouterTestingModule } from '@angular/router/testing';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';

import { PersonListPageComponent } from './person-list-page.component';
import { PersonService } from '../../services';

describe('PersonListPageComponent', () => {
  let component: PersonListPageComponent;
  let fixture: ComponentFixture<PersonListPageComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      schemas: [NO_ERRORS_SCHEMA],
      declarations: [ PersonListPageComponent ],
      imports: [ HttpClientTestingModule, RouterTestingModule, NoopAnimationsModule ],
      providers: [ PersonService ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(PersonListPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
