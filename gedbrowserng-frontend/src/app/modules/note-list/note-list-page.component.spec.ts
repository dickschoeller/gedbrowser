import { NO_ERRORS_SCHEMA } from '@angular/core';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { RouterTestingModule } from '@angular/router/testing';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';

import { NoteListPageComponent } from './note-list-page.component';
import { NoteService } from '../../services';

describe('NoteListPageComponent', () => {
  let component: NoteListPageComponent;
  let fixture: ComponentFixture<NoteListPageComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      schemas: [NO_ERRORS_SCHEMA],
      declarations: [ NoteListPageComponent ],
      imports: [ HttpClientTestingModule, RouterTestingModule, NoopAnimationsModule ],
      providers: [ NoteService ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(NoteListPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
